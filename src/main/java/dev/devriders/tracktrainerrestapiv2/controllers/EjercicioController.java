package dev.devriders.tracktrainerrestapiv2.controllers;


import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.services.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping(path = "/findAllEjercicios")
    public ArrayList<EjercicioModel> getEjercicios(){
        return this.ejercicioService.getEjercios();
    }

    @PostMapping(path = "/saveEjercicio")
    public EjercicioModel saveEjercicio(@RequestBody EjercicioModel ejercicio){
        return this.ejercicioService.saveEjercicio(ejercicio);
    }

    @GetMapping(path = "/{id}/idEjercicio")
    public Optional<EjercicioModel> getEjercicioById(@PathVariable("id") Long id){
        return this.ejercicioService.getById(id);
    }

    @PutMapping(path = "/{id}/UpdateEjercicioById")
    public EjercicioModel updateEjercicioById(@RequestBody EjercicioModel  Request,@PathVariable ("id") Long id){
        return this.ejercicioService.updateById(Request, id);
    }

    @DeleteMapping(path = "/{id}/deleteEjercicioById")
    public String deleteEjercicioById(@PathVariable("id") Long id){
        boolean ok = this.ejercicioService.deleteEjercicio(id);
        if(ok){
            return "Ejercicio with id="+id+" got deleted";
        } else{
            return "Error";
        }
    }
}
