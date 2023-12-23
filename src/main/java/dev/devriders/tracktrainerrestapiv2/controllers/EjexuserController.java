package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.*;
import dev.devriders.tracktrainerrestapiv2.repositories.IAmigosRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjexuserRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.EjexuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ejexuser")
public class EjexuserController {

    @Autowired
    private EjexuserService ejexuserService;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IEjercicioRepository ejercicioRepository;

    @Autowired
    private IEjexuserRepository ejexuserRepository;
    public EjexuserController(EjexuserService ejexuserService) {
        this.ejexuserService = ejexuserService;
    }

    @GetMapping(path = "/get-all")
    public ArrayList<EjexuserModel> getEjexusers(){
        return this.ejexuserService.getEjexusers();
    }

    @PostMapping(path = "/save-ejexuser")
    public EjexuserModel saveEjexuser(@RequestBody EjexuserModel ejexuser){
        return this.ejexuserService.saveEjexuser(ejexuser);
    }


    @GetMapping(path = "/get-by-id/{id}")
    public Optional<EjexuserModel> getEjexuserById (@PathVariable ("id") Long id){
        return this.ejexuserService.getById(id);
    }

   /* @GetMapping(path = "/get-by-user-id/{id}")
    public Optional<ArrayList<EjexuserModel>> getEjexuserByUserId (@PathVariable ("id") Long id){
        return this.ejexuserService.getByUserId(id);
    }*/

    @PutMapping(path = "/update/{id}")
    public EjexuserModel updateEjexuserById(@RequestBody EjexuserModel request, @PathVariable ("id") Long id) {
        return this.ejexuserService.updateById(request, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteById (@PathVariable ("id") Long id){
        boolean ok =this.ejexuserService.deleteEjexuser(id);
        if(ok){
            return " Ejexuser with id "+ id + " deleted";
        }
        else{
            return " Ejexuser with id "+ id + " not deleted, we have a problem";
        }
    }

    //Funciones prueba
    @GetMapping("/listar-ejexuser/{id}")
    public ResponseEntity<List<EjexuserModel>> getAllEjerciciosByUsuarioId(@PathVariable(value = "id") Long id) {
        if (!usuarioRepository.existsById(id)) {
            new ErrorResponse("Not found usuario with id = " + id);
        }
        List<EjexuserModel> ejexuser = ejexuserRepository.findByusuarioId(id);
        return new ResponseEntity<>(ejexuser, HttpStatus.OK);
    }

    @PostMapping("/crear-ejexuser/{id_usuario}/{id_ejercicio}")
    public ResponseEntity<EjexuserModel> createComment(@PathVariable(value = "id_usuario") Long id_usuario,
                                                     @PathVariable(value="id_ejercicio") Long id_ejercicio,
                                                     @RequestBody EjexuserModel request) {
        UsuarioModel user = usuarioRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        request.setUsuario(user);

        EjercicioModel friend = ejercicioRepository.findById(id_ejercicio)
                .orElseThrow(() -> new RuntimeException("Amigo no encontrado"));

        request.setEjercicio(friend);
        request.setPeso(request.getPeso());
        request.setTiempo(request.getTiempo());
        request.setCantidad(request.getCantidad());
        ejexuserRepository.save(request);

        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping("/update-ejexuser/{id}")
    public ResponseEntity<EjexuserModel> updateEjexuser(@PathVariable("id") long id, @RequestBody EjexuserModel Request) {
        EjexuserModel amigos = ejexuserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("relación no encontrada"));

        amigos.setCantidad(Request.getCantidad());
        amigos.setTiempo(Request.getTiempo());
        amigos.setPeso(Request.getPeso());

        return new ResponseEntity<>(ejexuserRepository.save(amigos), HttpStatus.OK);
    }

}
