package dev.devriders.tracktrainerrestapiv2.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.models.ErrorResponse;
import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import dev.devriders.tracktrainerrestapiv2.repositories.ICategoriaRepository;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import dev.devriders.tracktrainerrestapiv2.services.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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

    public EjercicioController(EjercicioService ejercicioService,
                               ICategoriaRepository categoriaRepository,
                               IEjercicioRepository ejercicioRepository) {
        this.ejercicioService = ejercicioService;
        this.categoriaRepository = categoriaRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    @GetMapping(path = "/find-all-ejercicios")
    public ArrayList<EjercicioModel> getEjercicios() {
        return ejercicioService.getEjercicios();
    }

    @PostMapping(path = "/save-ejercicio")
    public ResponseEntity<?> saveEjercicio(@RequestPart("ejercicio") String ejercicioJson,
                                           @RequestPart("imagen") MultipartFile imagen,
                                           @RequestPart("video") MultipartFile video) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EjercicioModel ejercicio = objectMapper.readValue(ejercicioJson, EjercicioModel.class);
            EjercicioModel savedEjercicio = ejercicioService.saveEjercicio(ejercicio, imagen, video);
            return ResponseEntity.ok(savedEjercicio);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar los datos del ejercicio");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar los archivos");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping(path = "/{id}/idE-ejercicio")
    public Optional<EjercicioModel> getEjercicioById(@PathVariable("id") Long id) {
        return ejercicioService.getById(id);
    }

    @PutMapping(path = "/{id}/update-ejercicio-by-id")
    public EjercicioModel updateEjercicioById(@RequestBody EjercicioModel request, @PathVariable("id") Long id) {
        return ejercicioService.updateById(request, id);
    }

    @DeleteMapping(path = "/{id}/delete-ejercicio-by-id")
    public String deleteEjercicioById(@PathVariable("id") Long id) {
        boolean ok = ejercicioService.deleteEjercicio(id);
        return ok ? "Ejercicio with id=" + id + " eliminado" : "Error";
    }

    @GetMapping("/categorias/{CategoriaId}/ejercicios")
    public ResponseEntity<List<EjercicioModel>> getAllEjerciciosByCategoriaId(@PathVariable(value = "CategoriaId") Long categoriaId) {
        if (!categoriaRepository.existsById(categoriaId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EjercicioModel> ejercicios = ejercicioRepository.findEjerciciosByCategoriasIdcategoria(categoriaId);
        return new ResponseEntity<>(ejercicios, HttpStatus.OK);
    }

    @GetMapping("/{ejercicioId}/categorias")
    public ResponseEntity<List<CategoriaModel>> getAllCategoriasByEjercicioId(@PathVariable(value = "ejercicioId") Long ejercicioId) {
        if (!ejercicioRepository.existsById(ejercicioId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CategoriaModel> categorias = categoriaRepository.findCategoriasByEjerciciosIdejercicio(ejercicioId);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PostMapping("/Categoria/{categoriaId}/ejercicio/{ejercicioId}")
    public ResponseEntity<EjercicioModel> addEjercicio(@PathVariable(value = "categoriaId") Long categoriaId, @PathVariable(value = "ejercicioId") Long ejercicioId, @RequestBody EjercicioModel ejercicioRequest) {
        EjercicioModel ejercicio = categoriaRepository.findById(categoriaId).map(categoria -> {
            if (ejercicioId != 0L) {
                EjercicioModel _ejercicio = ejercicioRepository.findById(ejercicioId)
                        .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));
                categoria.addEjercicio(_ejercicio);
                categoriaRepository.save(categoria);
                return _ejercicio;
            }

            categoria.addEjercicio(ejercicioRequest);
            return ejercicioRepository.save(ejercicioRequest);
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return new ResponseEntity<>(ejercicio, HttpStatus.CREATED);
    }

    @DeleteMapping("/Categoria/{categoriaId}/ejercicios/{ejercicioId}/delete")
    public ResponseEntity<HttpStatus> deleteEjercicioFromCategoria(@PathVariable(value = "categoriaId") Long categoriaId, @PathVariable(value = "ejercicioId") Long ejercicioId) {
        CategoriaModel categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.removeEjercicio(ejercicioId);
        categoriaRepository.save(categoria);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        ejercicioRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/imagen")
    public ResponseEntity<byte[]> getImagenEjercicio(@PathVariable Long id) {
        byte[] imagen = ejercicioService.getImagenEjercicio(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imagen);
    }

    @GetMapping(path = "/{id}/video")
    public ResponseEntity<byte[]> getVideoEjercicio(@PathVariable Long id) {
        byte[] video = ejercicioService.getVideoEjercicio(id)
                .orElseThrow(() -> new RuntimeException("Video no encontrado"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(video);
    }

}