package dev.devriders.tracktrainerrestapiv2.controllers;


import dev.devriders.tracktrainerrestapiv2.models.RutinaModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IRutinaRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:25513")
@RestController
@RequestMapping("/api/rutina")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @Autowired
    IRutinaRepository rutinaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public ArrayList<RutinaModel> getRutina(){
        return this.rutinaService.getRutina();
    }

    @PostMapping(path ="/guardar")
    public RutinaModel saveRutina(@RequestBody RutinaModel rutina){
        return this.rutinaService.saveRutina(rutina);
    }

    @GetMapping(path = "/{id}")
    public Optional<RutinaModel> getRutinaById(@PathVariable("id") Long id){
        return this.rutinaService.getById(id);
    }

    @PutMapping(path = "/{id}")
    public RutinaModel updateRutinaById(@RequestBody RutinaModel Request,@PathVariable ("id") Long id){
        return this.rutinaService.updateById(Request, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        boolean ok = this.rutinaService.deleteRutina(id);

        if(ok){
            return "Rutina "+ id +" Borrada";
        } else{
            return "Error, we have a problem mrpotatoxxx, can´t delete ejercicio with id "+ id;
        }
    }

    @GetMapping("/usuario/{Idusuario}/rutinas")
    public ResponseEntity<List<RutinaModel>> getAllRutinasByUsuariolId(@PathVariable(value = "Idusuario") Long Idusuario) {


        List<RutinaModel> rutinas = rutinaRepository.findByUsuario_Id(Idusuario);
        return new ResponseEntity<>(rutinas, HttpStatus.OK);
    }

    @PostMapping("/usuario/{Idusuario}/rutina")
    public ResponseEntity<RutinaModel> createRutina(@PathVariable(value = "Idusuario") Long id_usuario,
                                                 @RequestBody RutinaModel rutinaRequest) {
        RutinaModel rutina = usuarioRepository.findById(id_usuario).map(usuario -> {
            rutinaRequest.setUsuario(usuario);
            rutinaRequest.setNombreRutina(rutinaRequest.getNombreRutina());
            return rutinaRepository.save(rutinaRequest);
        }).orElseThrow();

        return new ResponseEntity<>(rutina, HttpStatus.CREATED);
    }
}
