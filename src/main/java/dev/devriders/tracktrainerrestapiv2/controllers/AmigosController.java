package dev.devriders.tracktrainerrestapiv2.controllers;


import dev.devriders.tracktrainerrestapiv2.models.AmigosModel;
import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IAmigosRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.AmigosService;
import dev.devriders.tracktrainerrestapiv2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amistad")
public class AmigosController {

    @Autowired
    private AmigosService amigosService;


    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IUsuarioRepository friendRepository;

    @Autowired
    private IAmigosRepository amigosRepository;

    @GetMapping("/find-all")
    public ArrayList<AmigosModel> getAmigos(){
        return this.amigosService.getAmigos();
    }

    @PostMapping("/save-amigo")
    public AmigosModel saveAmigo(@RequestBody AmigosModel amigo){
        return this.amigosService.saveAmigo(amigo);
    }

    @GetMapping("/find-amigo-by-id/{id}")
    public Optional<AmigosModel> getAmigoById(@PathVariable("id") Long id){
        return this.amigosService.getById(id);
    }

    @PostMapping("/update-by-id/{id}")
    public AmigosModel updateById(@RequestBody AmigosModel request,@PathVariable("id") Long id ){
        return this.amigosService.UpdateById(request, id);
    }

    @DeleteMapping("/Delete-amigo-by-id/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.amigosService.deleteUser(id);
        if (ok){
            return "Amigo with id "+id+" Deleted";
        }else{
            return "Amigo no eliminado";
        }
    }

    //Relaciones entre usuarios
    @GetMapping("/listar-amigos/{id}")
    public ResponseEntity<List<AmigosModel>> getAllAmigosByUsuarioId(@PathVariable(value = "id") Long id) {
        if (!usuarioRepository.existsById(id)) {
            new ErrorResponse("Not found usuario with id = " + id);
        }
        List<AmigosModel> amigos = amigosRepository.findByusuarioId(id);
        return new ResponseEntity<>(amigos, HttpStatus.OK);
    }

    @PostMapping("/crear-amistad/{id_usuario}/{id_amigo}")
    public ResponseEntity<AmigosModel> createComment(@PathVariable(value = "id_usuario") Long id_usuario,
                                                 @PathVariable(value="id_amigo") Long id_amigo,
                                                 @RequestBody AmigosModel request) {
            UsuarioModel user = friendRepository.findById(id_usuario)
                .orElseThrow(() -> new RuntimeException("Amigo no encontrado"));
            request.setUsuario(user);

            UsuarioModel friend = friendRepository.findById(id_amigo)
                    .orElseThrow(() -> new RuntimeException("Amigo no encontrado"));

            request.setAmigo(friend);
            request.setEstado(request.getEstado());
            request.setEliminacion(request.getEliminacion());
             amigosRepository.save(request);


        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PutMapping("/update-estado/{id}")
    public ResponseEntity<AmigosModel> updateAmistad(@PathVariable("id") long id, @RequestBody AmigosModel Request) {
        AmigosModel amigos = amigosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("relación no encontrada"));

        amigos.setEstado(Request.getEstado());
        amigos.setEliminacion(Request.getEliminacion());

        return new ResponseEntity<>(amigosRepository.save(amigos), HttpStatus.OK);
    }

}
// request.setUsuario(usuarioRepository.findById(id_usuario).get());
//        request.setAmigo(usuarioRepository.findById(id_amigo).get());
//        request.setEstado(request.getEstado());
//        request.setEliminacion(request.getEliminacion());
//        return new ResponseEntity<>(amigosRepository.save(request), HttpStatus.CREATED);//