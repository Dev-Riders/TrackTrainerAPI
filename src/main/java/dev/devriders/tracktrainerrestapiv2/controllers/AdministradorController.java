package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.EmailConfigs.EmailsenderAdmin;
import dev.devriders.tracktrainerrestapiv2.auth.JwtUtils;
import dev.devriders.tracktrainerrestapiv2.auth.PasswordEncoder;
import dev.devriders.tracktrainerrestapiv2.models.AdministradorModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IAdministradorRepository;
import dev.devriders.tracktrainerrestapiv2.services.AdministradorService;
import dev.devriders.tracktrainerrestapiv2.services.UsuarioService;
import dev.devriders.tracktrainerrestapiv2.utils.RandomCodeGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorController {
    private final AdministradorService administradorService;
    private final IAdministradorRepository administradorRepository;
    private final JwtUtils jwtUtils;
    private final EmailsenderAdmin emailsenderAdmin;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    public AdministradorController(AdministradorService administradorService,
                                   IAdministradorRepository administradorRepository,
                                   JwtUtils jwtUtils,
                                   EmailsenderAdmin emailsenderAdmin,
                                   PasswordEncoder passwordEncoder,
                                   UsuarioService usuarioService) {
        this.administradorService = administradorService;
        this.administradorRepository = administradorRepository;
        this.jwtUtils = jwtUtils;
        this.emailsenderAdmin = emailsenderAdmin;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }
    @PostMapping("/register")
    public ResponseEntity<String> saveAdministrador(@RequestBody AdministradorModel administrador) {
        administrador.setContraseña(passwordEncoder.encode(administrador.getContraseña()));
        AdministradorModel saveAdministrador = administradorService.saveAdministrador(administrador);
        if (saveAdministrador != null) {
            return ResponseEntity.ok("Administrador creado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetearContraseña(@RequestParam("code") String resetCode, @RequestBody AdministradorModel administrador) {
        Optional<AdministradorModel> administradorOptional = administradorRepository.findByResetCode(resetCode);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de restablecimiento de contraseña inválido");
        }
        AdministradorModel administradorExistente = administradorOptional.get();
        administradorExistente.setContraseña(passwordEncoder.encode(administrador.getContraseña()));
        administradorExistente.setResetCode(null);
        administradorRepository.save(administradorExistente);
        return ResponseEntity.ok("Contraseña restablecida exitosamente");
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String correo) {
        Optional<AdministradorModel> administradorOptional = administradorRepository.findByCorreo(correo);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe un usuario con ese correo");
        }
        AdministradorModel administrador = administradorOptional.get();
        String resetCode = RandomCodeGenerator.generateRandomCode();
        administrador.setResetCode(resetCode);
        administradorRepository.save(administrador);
        emailsenderAdmin.sendPasswordResetEmailAdmin(administrador);
        return ResponseEntity.ok("Se ha enviado un correo para restablecer la contraseña");
    }
    @PostMapping("/login")
    public ResponseEntity<String> iniciarSesionAdministrador(@RequestBody AdministradorModel administrador){
        Optional<AdministradorModel> administradorOptional = administradorRepository.findByCorreo(administrador.getCorreo());
        if (administradorOptional.isEmpty() || !passwordEncoder.match(administrador.getContraseña(), administradorOptional.get().getContraseña())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
        AdministradorModel administradorExistente = administradorOptional.get();
        administradorRepository.save(administradorExistente);
        String token = jwtUtils.generateToken(administradorExistente.getCorreo());
        return ResponseEntity.ok(token);
    }
    @GetMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> getAllUsuarios(@RequestHeader("Authorization") String token,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(required = false) String correo,
                                                              @RequestParam(required = false) String nickname) {
        String correoAdministrador = jwtUtils.extractUsername(token);

        Optional<AdministradorModel> administradorOptional = administradorService.getAdministradorByCorreo(correoAdministrador);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioModel> usuarioPage;

        if (correo != null && nickname != null) {
            usuarioPage = usuarioService.findByCorreoAndNickname(correo, nickname, pageable);
        } else if (correo != null) {
            usuarioPage = usuarioService.findByCorreo(correo, pageable);
        } else if (nickname != null) {
            usuarioPage = usuarioService.findByNickname(nickname, pageable);
        } else {
            usuarioPage = usuarioService.getUsers(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("usuarios", usuarioPage.getContent());
        response.put("currentPage", usuarioPage.getNumber());
        response.put("totalItems", usuarioPage.getTotalElements());
        response.put("totalPages", usuarioPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioModel> getUsuarioById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        String correoAdministrador = jwtUtils.extractUsername(token);

        Optional<AdministradorModel> administradorOptional = administradorService.getAdministradorByCorreo(correoAdministrador);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UsuarioModel> usuarioOptional = usuarioService.getById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModel usuario = usuarioOptional.get();
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioModel>> getUsuariosTodos(@RequestHeader("Authorization") String token) {
        String correoAdministrador = jwtUtils.extractUsername(token);

        Optional<AdministradorModel> administradorOptional = administradorService.getAdministradorByCorreo(correoAdministrador);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        List<UsuarioModel> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    @PatchMapping("/usuarios/{id}/disable")
    public ResponseEntity<String> disableUsuario(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        String correoAdministrador = jwtUtils.extractUsername(token);

        Optional<AdministradorModel> administradorOptional = administradorService.getAdministradorByCorreo(correoAdministrador);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UsuarioModel> usuarioOptional = usuarioService.getById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModel usuario = usuarioOptional.get();
        AdministradorModel administrador = administradorOptional.get();

        usuario.setEliminado(true);
        usuario.setFechaEliminacion(LocalDateTime.now());
        usuario.setQuienElimino(administrador);
        usuario.setFechaActualizacion(LocalDateTime.now());
        usuario.setQuienActualizo(administrador);

        usuarioService.saveUsuario(usuario);

        return ResponseEntity.ok("Usuario deshabilitado exitosamente");
    }
    @PatchMapping("/usuarios/{id}/enable")
    public ResponseEntity<String> enableUsuario(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        String correoAdministrador = jwtUtils.extractUsername(token);

        Optional<AdministradorModel> administradorOptional = administradorService.getAdministradorByCorreo(correoAdministrador);
        if (administradorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UsuarioModel> usuarioOptional = usuarioService.getById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UsuarioModel usuario = usuarioOptional.get();
        AdministradorModel administrador = administradorOptional.get();

        usuario.setEliminado(false);
        usuario.setFechaEliminacion(null);
        usuario.setQuienElimino(null);
        usuario.setFechaActualizacion(LocalDateTime.now());
        usuario.setQuienActualizo(administrador);

        usuarioService.saveUsuario(usuario);

        return ResponseEntity.ok("Usuario habilitado exitosamente");
    }

}
