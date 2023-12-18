package dev.devriders.tracktrainerrestapiv2.controllers;


import dev.devriders.tracktrainerrestapiv2.models.AmigosModel;
import dev.devriders.tracktrainerrestapiv2.services.AmigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/amistad")
public class AmigosController {

    @Autowired
    private AmigosService amigosService;

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
}
