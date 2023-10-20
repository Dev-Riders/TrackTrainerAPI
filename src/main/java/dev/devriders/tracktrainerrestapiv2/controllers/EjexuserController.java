package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.EjexuserModel;
import dev.devriders.tracktrainerrestapiv2.services.EjexuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/ejexuser")
public class EjexuserController {

    @Autowired
    private EjexuserService ejexuserService;


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

    @GetMapping(path = "/get-by-user-id/{id}")
    public Optional<ArrayList<EjexuserModel>> getEjexuserByUserId (@PathVariable ("id") Long id){
        return this.ejexuserService.getByUserId(id);
    }

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

}
