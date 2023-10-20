package dev.devriders.tracktrainerrestapiv2.controllers;


import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import dev.devriders.tracktrainerrestapiv2.repositories.ICategoriaRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import dev.devriders.tracktrainerrestapiv2.services.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {

    @Autowired
    private EjercicioService ejercicioService;
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private IEjercicioRepository ejercicioRepository;
    @GetMapping(path = "/find-all-ejercicios")
    public ArrayList<EjercicioModel> getEjercicios(){
        return this.ejercicioService.getEjercios();
    }

    @PostMapping(path = "/save-ejercicio")
    public EjercicioModel saveEjercicio(@RequestBody EjercicioModel ejercicio){
        return this.ejercicioService.saveEjercicio(ejercicio);
    }

    @GetMapping(path = "/{id}/idE-ejercicio")
    public Optional<EjercicioModel> getEjercicioById(@PathVariable("id") Long id){
        return this.ejercicioService.getById(id);
    }

    @PutMapping(path = "/{id}/update-ejercicio-by-id")
    public EjercicioModel updateEjercicioById(@RequestBody EjercicioModel  Request,@PathVariable ("id") Long id){
        return this.ejercicioService.updateById(Request, id);
    }

    @DeleteMapping(path = "/{id}/delete-ejercicio-by-id")
    public String deleteEjercicioById(@PathVariable("id") Long id){
        boolean ok = this.ejercicioService.deleteEjercicio(id);
        if(ok){
            return "Ejercicio with id="+id+" got deleted";
        } else{
            return "Error";
        }
    }
    @GetMapping("/categorias/{CategoriaId}/ejercicios")
    public ResponseEntity<List<EjercicioModel>> getAllEjerciciosByCategoriaId(@PathVariable(value = "CategoriaId") Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            new ErrorResponse("Not found categoria with id = " + categoriaId);
        }

        List<EjercicioModel> ejercicios = ejercicioRepository.findEjerciciosByCategoriasIdcategoria(categoriaId);
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }
    @GetMapping("/{ejercicioId}/categorias")
    public ResponseEntity<List<CategoriaModel>> getAllCategoriasByEjercicioId(@PathVariable(value = "ejercicioId") Long ejercicioId) {
        if (!ejercicioRepository.existsById(ejercicioId)) {
            new ErrorResponse("Not found ejercicio with id = " + ejercicioId);
        }

        List<CategoriaModel> categorias = categoriaRepository.findCategoriasByEjerciciosIdejercicio(ejercicioId);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }
    @PostMapping("/Categoria/{categoriaId}/ejercicio/{ejercicioId}")
    public ResponseEntity<EjercicioModel> addEjercicio(@PathVariable(value = "categoriaId") Long categoriaId, @PathVariable(value = "ejercicioId") Long ejercicioId, @RequestBody EjercicioModel ejercicioRequest) {
        EjercicioModel ejercicio = categoriaRepository.findById(categoriaId).map(categoria-> {
            //long Idejercicio = ejercicioRequest.getIdejercicio();

            // tag is existed
            if (ejercicioId != 0L) {
                EjercicioModel _ejercicio = ejercicioRepository.findById(ejercicioId)
                        .orElseThrow();
                categoria.addEjercicio(_ejercicio);
                categoriaRepository.save(categoria);
                return _ejercicio;
            }

            // add and create new Tag
            categoria.addEjercicio(ejercicioRequest);
            return ejercicioRepository.save(ejercicioRequest);
        }).orElseThrow();

        return new ResponseEntity<>(ejercicio, HttpStatus.CREATED);
    }
    @DeleteMapping("/Categoria/{categoriaId}/ejercicios/{ejercicioId}/delete")
    public ResponseEntity<HttpStatus> deleteEjercicioFromCategoria(@PathVariable(value = "categoriaId") Long categoriaId, @PathVariable(value = "ejercicioId") Long ejercicioId) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow();

        categoria.removeEjercicio(ejercicioId);
        categoriaRepository.save(categoria);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        ejercicioRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

