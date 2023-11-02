package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.PuntajeModel;
import dev.devriders.tracktrainerrestapiv2.services.PuntajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntaje")
public class PuntajeController {

    @Autowired
    private PuntajeService puntajeService;

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

}
