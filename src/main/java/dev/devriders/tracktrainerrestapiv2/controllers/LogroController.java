package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.*;
import dev.devriders.tracktrainerrestapiv2.repositories.ILogroRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.LogroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LogroController {
    @Autowired
    private LogroService logroService;

    @Autowired
    private ILogroRepository logroRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @GetMapping("/logro/listar")
    public ArrayList<LogroModel> getLogro(){
        return this.logroService.getLogro();
    }
    @PostMapping(path ="/logro/guardar")
    public LogroModel saveLogro(@RequestBody LogroModel logro){
        return this.logroService.saveLogro(logro);
    }
    @GetMapping(path = "/logro/{id}")
    public Optional<LogroModel> getLogroById(@PathVariable("id") Long id){
        return this.logroService.getById(id);
    }
    @PutMapping(path = "/logro/{id}")
    public LogroModel updatelogroById(@RequestBody LogroModel Request,@PathVariable ("id") Long id){
        return this.logroService.updateById(Request, id);
    }
    @DeleteMapping(path = "/logro/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        boolean ok = this.logroService.deleteLogro(id);
        if(ok){
            return "Logro "+ id +" Borrado";
        } else{
            return "Error, we have a problem mrpotatoxxx, can´t delete logro with id "+ id;
        }
    }

    //Inicio controlador many to many
    @GetMapping("/logro/usuario/{Idusuario}/logro1")
    public ResponseEntity<List<LogroModel>> getAllLogrosByIdusuario(@PathVariable(value = "Idusuario") Long Idusuario) {
        if (!usuarioRepository.existsById(Idusuario)) {
            new ErrorResponse("Not found Usuario with id = " + Idusuario);
        }

        List<LogroModel> Logros = logroRepository.findLogrosByUsuariosId(Idusuario);
        return new ResponseEntity<>(Logros, HttpStatus.OK);
    }



    @GetMapping("/logro/{Idlogro}/usuario")
    public ResponseEntity<List<UsuarioModel>> getAllUsuariosByIdlogro(@PathVariable(value = "Idlogro") Long Idlogro) {
        if (!logroRepository.existsById(Idlogro)) {
            new ErrorResponse("Not found Logro with id = " + Idlogro);
        }

        List<UsuarioModel> usuario = usuarioRepository.findUsuarioByLogrosIdlogro(Idlogro);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PostMapping("/logro/usuario/{Idusuario}/logro2/{Idlogro}")
    public ResponseEntity<LogroModel> addLogro(@PathVariable(value = "Idusuario") Long Idusuario,@PathVariable(value = "Idlogro") Long Idlogro, @RequestBody LogroModel logroRequest) {
        LogroModel logro = usuarioRepository.findById(Idusuario).map(usuario -> {
            //long Idejercicio = ejercicioRequest.getIdejercicio();

            // tag is existed
            if (Idlogro != 0L) {
                LogroModel _logro= logroRepository.findById(Idlogro)
                        .orElseThrow();
                usuario.addLogro(_logro);
                usuarioRepository.save(usuario);
                return _logro;
            }

            // add and create new Tag
            usuario.addLogro(logroRequest);
            return logroRepository.save(logroRequest);
        }).orElseThrow();

        return new ResponseEntity<>(logro, HttpStatus.CREATED);
    }


    @DeleteMapping("/logro/usuario/{Idusuario}/logro/{Idlogro}")
    public ResponseEntity<HttpStatus> deleteLogroFromUsuario(@PathVariable(value = "Idusuario") Long Idusuario, @PathVariable(value = "Idlogro") Long Idlogro) {
        UsuarioModel usuario = usuarioRepository.findById(Idusuario)
                .orElseThrow();

        usuario.removeLogro(Idlogro);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //Fin controlador
}