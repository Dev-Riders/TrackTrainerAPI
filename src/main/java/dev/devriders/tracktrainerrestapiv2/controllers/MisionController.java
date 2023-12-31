package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IMisionRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import dev.devriders.tracktrainerrestapiv2.services.MisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mision")
public class MisionController {
    @Autowired
    private MisionService misionService;

    @Autowired
    private IMisionRepository misionRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public MisionController(MisionService misionService,
                            IMisionRepository misionRepository,
                            IUsuarioRepository usuarioRepository) {
        this.misionService = misionService;
        this.misionRepository = misionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/listar")
    public ArrayList<MisionModel> getMision(){
        return this.misionService.getMision();
    }
    @PostMapping(path ="/guardar")
    public MisionModel saveMision(@RequestBody MisionModel mision){
        return this.misionService.saveMision(mision);
    }
    @GetMapping(path = "/find-by-id/{id}")
    public Optional<MisionModel> getMisionById(@PathVariable("id") Long id){
        return this.misionService.getById(id);
    }
    @PutMapping(path = "/update-by-id/{id}")
    public MisionModel updateMisionById(@RequestBody MisionModel Request,@PathVariable ("id") Long id){
        return this.misionService.updateById(Request, id);
    }
    @DeleteMapping(path = "/deleteby-id/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        boolean ok = this.misionService.deleteMision(id);
        if(ok){
            return "Mision "+ id +" Borrado";
        } else{
            return "Error, we have a problem mrpotatoxxx, can´t delete mision with id "+ id;
        }
    }

    //Inicio controlador many to many
    @GetMapping("/usuario/{Idusuario}/getmision-by-id-usuario")
    public ResponseEntity<List<MisionModel>> getAllMisionesByIdusuario(@PathVariable(value = "Idusuario") Long Idusuario) {
        if (!usuarioRepository.existsById(Idusuario)) {
            new ErrorResponse("Not found usuario with id = " + Idusuario);
        }

        List<MisionModel> Misiones = misionRepository.findMisionesByUsuariosId(Idusuario);
        return new ResponseEntity<>(Misiones, HttpStatus.OK);
    }



    @GetMapping("/{Idmision}/get-usuario-by-id-mision")
    public ResponseEntity<List<UsuarioModel>> getAllUsuariosByIdmision(@PathVariable(value = "Idmision") Long Idmision) {
        if (!misionRepository.existsById(Idmision)) {
            new ErrorResponse("Not found mision with id = " + Idmision);
        }

        List<UsuarioModel> Usuarios = usuarioRepository.findUsuarioByMisionesIdmision(Idmision);
        return new ResponseEntity<>(Usuarios, HttpStatus.OK);
    }

    @PostMapping("/usuario/{Idusuario}/add-mision/{Idmision}")
    public ResponseEntity<MisionModel> addMision(@PathVariable(value = "Idusuario") Long Idusuario,@PathVariable(value = "Idmision") Long Idmision, @RequestBody MisionModel misionRequest) {
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
    }


    @DeleteMapping("/usuario/{Idusuario}/delete-mision-from-usuario/{Idmision}")
    public ResponseEntity<HttpStatus> deleteMisionFromUsuario(@PathVariable(value = "Idusuario") Long Idusuario, @PathVariable(value = "Idmision") Long Idmision) {
        UsuarioModel usuario = usuarioRepository.findById(Idusuario)
                .orElseThrow();

        usuario.removeMision(Idmision);
        usuarioRepository.save(usuario);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //Fin controlador
}
