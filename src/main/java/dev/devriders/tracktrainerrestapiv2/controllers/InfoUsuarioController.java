package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.InfoUsuarioModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.services.InfoUsuarioService;
import dev.devriders.tracktrainerrestapiv2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/info-usuarios")
public class InfoUsuarioController {

    private final InfoUsuarioService infoUsuarioService;
    private final UsuarioService usuarioService;
    @Autowired
    public InfoUsuarioController(InfoUsuarioService infoUsuarioService,
                                 UsuarioService usuarioService) {
        this.infoUsuarioService = infoUsuarioService;
        this.usuarioService = usuarioService;
    }
    @GetMapping
    public ResponseEntity<List<InfoUsuarioModel>> getAllInfoUsuarios() {
        List<InfoUsuarioModel> infoUsuarios = infoUsuarioService.getAllInfoUsuarios();
        return new ResponseEntity<>(infoUsuarios, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveInfoUsuario(@RequestBody InfoUsuarioModel infoUsuario) {
        Optional<UsuarioModel> usuarioOptional = usuarioService.getById(infoUsuario.getUsuarioId());
        if (usuarioOptional.isPresent()) {
            InfoUsuarioModel savedInfoUsuario = infoUsuarioService.saveInfoUsuario(infoUsuario);
            if (savedInfoUsuario != null) {
                return new ResponseEntity<>("La información se guardó exitosamente.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Error al guardar la información.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/info/{id}")
    public Optional<InfoUsuarioModel> getInfoUsuarioById(@PathVariable("id") Long id){
        return this.infoUsuarioService.getById(id);
    }
    @GetMapping(path = "/imc/{id}")
    public Float getImc(@PathVariable("id") Long id){
        return this.infoUsuarioService.IMC(id);
    }
}
