package dev.devriders.tracktrainerrestapiv2.controllers;

import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import dev.devriders.tracktrainerrestapiv2.repositories.ICategoriaRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import dev.devriders.tracktrainerrestapiv2.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private IEjercicioRepository ejercicioRepository;
    @Autowired
    private ICategoriaRepository categoriaRepository;


    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping(path = "/find-all-categorias")

    public ArrayList<CategoriaModel> getCategorias(){
        return this.categoriaService.getCategorias();
    }

    @PostMapping(path = "/save-categoria")
    public CategoriaModel saveCategoria(@RequestBody CategoriaModel categoria){
        return this.categoriaService.saveCategoria(categoria);
    }

    @GetMapping(path = "/{id}/id-categoria")
    public Optional<CategoriaModel> getCategoriaById(@PathVariable("id") Long id){
        return this.categoriaService.getById(id);
    }

    @PutMapping(path = "/{id}/update-categoria-by-id")
    public CategoriaModel updateCategoriaById(@RequestBody CategoriaModel  Request,@PathVariable ("id") Long id){
        return this.categoriaService.updateById(Request, id);
    }

    @DeleteMapping(path = "/{id}/delete-categoria-by-id")
    public String deleteCategoriaById(@PathVariable("id") Long id){
        boolean ok = this.categoriaService.deleteCategoria(id);
        if(ok){
            return "Categoria with id="+id+" got deleted";
        } else{
            return "Error";
        }
    }

    @GetMapping("/{ejercicioId}/categorias")
    public ResponseEntity<CategoriaModel> getAllCategoriasByEjercicioId(@PathVariable(value = "ejercicioId") Long ejercicioId) {
        if (!ejercicioRepository.existsById(ejercicioId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CategoriaModel categorias = categoriaRepository.findCategoriasByEjerciciosIdejercicio(ejercicioId);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
}
