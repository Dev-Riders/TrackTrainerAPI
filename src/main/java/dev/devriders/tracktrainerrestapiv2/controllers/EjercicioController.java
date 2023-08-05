package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.RutinaModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IRutinaRepository;
import dev.devriders.tracktrainerrestapiv2.services.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EjercicioController {

    @Autowired
    private IRutinaRepository rutinaRepository;

    @Autowired
    private IEjercicioRepository ejercicioRepository;

    @Autowired
    private EjercicioService ejercicioService;
    @GetMapping("/ejercicio/listar")
    public ArrayList<EjercicioModel> getEjercicio(){
        return this.ejercicioService.getEjercicio();
    }
    @PostMapping(path ="/ejercicio/guardar")
    public EjercicioModel saveEjercicio(@RequestBody EjercicioModel ejercicio){
        return this.ejercicioService.saveEjercicio(ejercicio);
    }
    @GetMapping(path = "/ejercicio/{id}")
    public Optional<EjercicioModel> getEjercicioById(@PathVariable("id") Long id){
        return this.ejercicioService.getById(id);
    }
    @PutMapping(path = "/ejercicio/{id}")
    public EjercicioModel updateEjercicioById(@RequestBody EjercicioModel Request,@PathVariable ("id") Long id){
        return this.ejercicioService.updateById(Request, id);
    }
    @DeleteMapping(path = "/ejercicio/{id}")
    public String deleteById(@PathVariable ("id") Long id){
        boolean ok = this.ejercicioService.deleteEjercicio(id);
        if(ok){
            return "Ejercicio "+ id +" Borrado";
        } else{
            return "Error, we have a problem mrpotatoxxx, can´t delete ejercicio with id "+ id;
        }
    }

    @GetMapping("/ejercicio/rutina/{Idrutina}/ejercicio1")
    public ResponseEntity<List<EjercicioModel>> getAllEjerciciosByIdrutina(@PathVariable(value = "Idrutina") Long Idrutina) {
        if (!rutinaRepository.existsById(Idrutina)) {
            new ErrorResponse("Not found Rutina with id = " + Idrutina);
        }

        List<EjercicioModel> Ejercicios = ejercicioRepository.findEjercicioByRutinasIdrutina(Idrutina);
        return new ResponseEntity<>(Ejercicios, HttpStatus.OK);
    }



    @GetMapping("/ejercicio/{Idejercicio}/Rutina")
    public ResponseEntity<List<RutinaModel>> getAllTutorialsByIdejercicio(@PathVariable(value = "Idejercicio") Long Idejercicio) {
        if (!ejercicioRepository.existsById(Idejercicio)) {
            new ErrorResponse("Not found Rutina with id = " + Idejercicio);
        }

        List<RutinaModel> Rutinas = rutinaRepository.findRutinaByEjerciciosIdejercicio(Idejercicio);
        return new ResponseEntity<>(Rutinas, HttpStatus.OK);
    }

    @PostMapping("/ejercicio/rutina/{Idrutina}/ejercicios2/{Idejercicio}")
    public ResponseEntity<EjercicioModel> addEjercicio(@PathVariable(value = "Idrutina") Long Idrutina,@PathVariable(value = "Idejercicio") Long Idejercicio, @RequestBody EjercicioModel ejercicioRequest) {
        EjercicioModel ejercicio = rutinaRepository.findById(Idrutina).map(rutina -> {
            //long Idejercicio = ejercicioRequest.getIdejercicio();

            // tag is existed
            if (Idejercicio != 0L) {
                EjercicioModel _ejercicio = ejercicioRepository.findById(Idejercicio)
                        .orElseThrow();
                rutina.addEjercicio(_ejercicio);
                rutinaRepository.save(rutina);
                return _ejercicio;
            }

            // add and create new Tag
            rutina.addEjercicio(ejercicioRequest);
            return ejercicioRepository.save(ejercicioRequest);
        }).orElseThrow();

        return new ResponseEntity<>(ejercicio, HttpStatus.CREATED);
    }


    @DeleteMapping("/ejercicio/rutina/{Idrutina}/ejercicio/{Idejercicio}")
    public ResponseEntity<HttpStatus> deleteEjercicioFromRutina(@PathVariable(value = "Idrutina") Long Idrutina, @PathVariable(value = "Idejercicio") Long Idejercicio) {
        RutinaModel rutina = rutinaRepository.findById(Idrutina)
                .orElseThrow();

        rutina.removeEjercicio(Idejercicio);
        rutinaRepository.save(rutina);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
