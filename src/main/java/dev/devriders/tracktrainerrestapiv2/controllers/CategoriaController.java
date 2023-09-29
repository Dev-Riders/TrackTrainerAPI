package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import dev.devriders.tracktrainerrestapiv2.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
@RestController
@RequestMapping("/api")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(path = "/findAllCategorias")
    public ArrayList<CategoriaModel> getCategorias(){
        return this.categoriaService.getCategorias();
    }

    @PostMapping(path = "/saveCategoria")
    public CategoriaModel saveCategoria(@RequestBody CategoriaModel categoria){
        return this.categoriaService.saveCategoria(categoria);
    }

    @GetMapping(path = "/{id}/idCategoria")
    public Optional<CategoriaModel> getCategoriaById(@PathVariable("id") Long id){
        return this.categoriaService.getById(id);
    }

    @PutMapping(path = "/{id}/UpdateCategoriaById")
    public CategoriaModel updateCategoriaById(@RequestBody CategoriaModel  Request,@PathVariable ("id") Long id){
        return this.categoriaService.updateById(Request, id);
    }

    @DeleteMapping(path = "/{id}/deleteCategoriaById")
    public String deleteCategoriaById(@PathVariable("id") Long id){
        boolean ok = this.categoriaService.deleteCategoria(id);
        if(ok){
            return "Categoria with id="+id+" got deleted";
        } else{
            return "Error";
        }
    }
}
