package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import dev.devriders.tracktrainerrestapiv2.models.PuntajeModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IPuntajeRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.PuntajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntaje")
public class PuntajeController {

    @Autowired
    private PuntajeService puntajeService;

    @Autowired
    private IPuntajeRepository puntajeRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @GetMapping("/listar")
    public ArrayList<PuntajeModel> getPuntajes(){
        return this.puntajeService.getPuntajes();
    }

    @PostMapping("/guardar")
    public PuntajeModel savePuntaje (@RequestBody PuntajeModel puntaje){
        return this.puntajeService.savePuntaje(puntaje);
    }

    @GetMapping("/finById/{id}")
    public Optional<PuntajeModel> getPuntajeById(@PathVariable(value ="id") Long id){
        return this.puntajeService.getById(id);
    }

    @PutMapping("/update/{id}")
    public PuntajeModel updatePuntajeByID(@RequestBody PuntajeModel Request,@PathVariable ("id") Long id){
        return this.puntajeService.updateById(Request, id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        boolean ok = this.puntajeService.deletePuntaje(id);

        if(ok){
            return "Puntaje with id: "+id+"deleted";
        }else{
            return "Puntaje with id: "+id+" not deleted";
        }
    }

    //Trabajo en proceso--------------------------------------------------------------------------------------------------------------
    @GetMapping("/{Idusuario}/get-puntaje-by-id-usuario")
    public ResponseEntity<List<PuntajeModel>> getPuntrajeByIdUsuario(@PathVariable(value = "Idusuario") Long Idusuario) {
        if (!usuarioRepository.existsById(Idusuario)) {
            new ErrorResponse("Not found usuario with id = " + Idusuario);
        }

        List<PuntajeModel> Puntaje = puntajeRepository.findPuntajesByUsuariosid(Idusuario);
        return new ResponseEntity<>(Puntaje, HttpStatus.OK);
    }
    /*
    @PostMapping("/usuario/{Idusuario}/add-mision/{Idmision}")
    public ResponseEntity<MisionModel> addMision(@PathVariable(value = "Idusuario") Long Idusuario, @PathVariable(value = "Idmision") Long Idmision, @RequestBody MisionModel misionRequest) {
        MisionModel mision = usuarioRepository.findById(Idusuario).map(usuario -> {
            //long Idejercicio = ejercicioRequest.getIdejercicio();

            // tag is existed
            if (Idmision != 0L) {
                MisionModel _mision = misionRepository.findById(Idmision)
                        .orElseThrow();
                usuario.addMision(_mision);
                usuarioRepository.save(usuario);
                return _mision;
            }

            // add and create new Tag
            usuario.addMision(misionRequest);
            return misionRepository.save(misionRequest);
        }).orElseThrow();

        return new ResponseEntity<>(mision, HttpStatus.CREATED);
    }*/
}
