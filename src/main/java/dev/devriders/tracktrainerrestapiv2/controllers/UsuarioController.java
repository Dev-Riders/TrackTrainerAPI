package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.auth.JwtUtils;
import dev.devriders.tracktrainerrestapiv2.auth.PasswordEncoder;
import dev.devriders.tracktrainerrestapiv2.EmailConfigs.EmailSender;
import dev.devriders.tracktrainerrestapiv2.models.InfoUsuarioModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.InfoUsuarioService;
import dev.devriders.tracktrainerrestapiv2.services.UsuarioService;
import dev.devriders.tracktrainerrestapiv2.utils.RandomCodeGenerator;
import dev.devriders.tracktrainerrestapiv2.utils.UsuarioInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {




    private final UsuarioService usuarioService;
    private final IUsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final InfoUsuarioService infoUsuarioService;
    public UsuarioController(UsuarioService usuarioService,
                             IUsuarioRepository usuarioRepository,
                             JwtUtils jwtUtils,
                             EmailSender emailSender,
                             PasswordEncoder passwordEncoder,
                             InfoUsuarioService infoUsuarioService) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.infoUsuarioService = infoUsuarioService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> saveUsuario(@RequestBody UsuarioModel usuario) {
        String verificationCode = RandomCodeGenerator.generateRandomCode();
        usuario.setVerificationCode(verificationCode);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        UsuarioModel savedUsuario = usuarioService.saveUsuario(usuario);
        emailSender.sendVerificationEmailUsuario(savedUsuario);

        if (savedUsuario != null) {
            return ResponseEntity.ok("Usuario creado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesion(@RequestBody UsuarioModel usuario) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (usuarioOptional.isEmpty() || !passwordEncoder.match(usuario.getContraseña(), usuarioOptional.get().getContraseña())) {
            // Verificar si la cuenta está bloqueada debido a intentos fallidos anteriores
            if (usuarioOptional.isPresent() && usuarioOptional.get().getIntentosFallidos() >= 3) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La cuenta está bloqueada. Por favor, comunícate con el soporte técnico.");
            }
            // Incrementar el contador de intentos fallidos
            if (usuarioOptional.isPresent()) {
                UsuarioModel usuarioExistente = usuarioOptional.get();
                usuarioExistente.setIntentosFallidos(usuarioExistente.getIntentosFallidos() + 1);
                // Verificar si se alcanzó el límite de intentos fallidos
                if (usuarioExistente.getIntentosFallidos() >= 3) {
                    usuarioExistente.setBloqueado(true);
                }
                usuarioRepository.save(usuarioExistente);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
        UsuarioModel usuarioExistente = usuarioOptional.get();
        // Verificar si la cuenta está bloqueada
        if (usuarioExistente.isBloqueado()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La cuenta está bloqueada. Por favor, comunícate con el soporte técnico.");
        }
        // Verificar si el usuario está verificado
        if (!usuarioExistente.isVerified()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La cuenta no está verificada. Por favor, verifica tu cuenta antes de iniciar sesión.");
        }

        // Verificar si la cuenta está eliminada
        if (usuarioExistente.isEliminado()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La cuenta ha sido eliminada. Por favor, comunícate con un administrador para más información.");
        }

        // Restablecer el contador de intentos fallidos al iniciar sesión exitosamente
        usuarioExistente.setIntentosFallidos(0);
        usuarioRepository.save(usuarioExistente);
        String token = jwtUtils.generateToken(usuarioExistente.getCorreo());
        return ResponseEntity.ok(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> updateUsuarioById(@RequestBody UsuarioModel request, @PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Optional<UsuarioModel> usuarioOptional = usuarioService.getById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UsuarioModel usuarioExistente = usuarioOptional.get();
        usuarioExistente.setNombre(request.getNombre());
        usuarioExistente.setApellido(request.getApellido());
        usuarioExistente.setNickname(request.getNickname());
        usuarioExistente.setCorreo(request.getCorreo());
        usuarioExistente.setContraseña(request.getContraseña());

        UsuarioModel usuarioActualizado = usuarioService.saveUsuario(usuarioExistente);
        return ResponseEntity.ok(usuarioActualizado);
    }
    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("code") String verificationCode) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByVerificationCode(verificationCode);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setVerified(true);
            usuario.setVerificationCode(null);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("La cuenta ha sido verificada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de verificación inválido");
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> enviarCodigoRecuperacion(@RequestParam("email") String correo) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        UsuarioModel usuario = usuarioOptional.get();
        String resetCode = RandomCodeGenerator.generateRandomCode();
        usuario.setResetCode(resetCode);
        usuarioRepository.save(usuario);
        emailSender.sendPasswordResetEmailUsuario(usuario);
        return ResponseEntity.ok("Se ha enviado un código de recuperación a tu correo electrónico");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetearContraseña(@RequestParam("code") String resetCode, @RequestBody UsuarioModel usuario) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByResetCode(resetCode);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de restablecimiento de contraseña inválido");
        }
        UsuarioModel usuarioExistente = usuarioOptional.get();
        usuarioExistente.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuarioExistente.setResetCode(null);
        usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok("Contraseña restablecida exitosamente");
    }
    @GetMapping("/me")
    public ResponseEntity<UsuarioInfoResponse> obtenerUsuarioActual(@RequestHeader("Authorization") String token) {
        if (token == null || !jwtUtils.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String correo = jwtUtils.extractUsername(token); // Extraer el correo electrónico del token

        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByCorreo(correo);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModel usuario = usuarioOptional.get();
        Optional<InfoUsuarioModel> infoUsuarioOptional = infoUsuarioService.getInfoUsuarioById(usuario.getId());
        UsuarioInfoResponse response = new UsuarioInfoResponse(usuario, infoUsuarioOptional.orElse(null));
        return ResponseEntity.ok(response);
    }


}
