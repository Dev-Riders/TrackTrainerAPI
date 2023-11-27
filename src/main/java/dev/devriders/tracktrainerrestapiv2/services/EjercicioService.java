package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EjercicioService {

    @Autowired
    IEjercicioRepository ejercicioRepository;

    public ArrayList<EjercicioModel> getEjercicios() {
        return (ArrayList<EjercicioModel>) ejercicioRepository.findAll();
    }

    public EjercicioModel saveEjercicio(EjercicioModel ejercicio, MultipartFile imagenFile, MultipartFile videoFile) {
        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                validateFileSize(imagenFile.getSize());
                ejercicio.setImagen_ejercicio(imagenFile.getBytes());
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                validateFileSize(videoFile.getSize());
                ejercicio.setVideo_ejercicio(videoFile.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar archivo: " + e.getMessage());
        }
        return ejercicioRepository.save(ejercicio);
    }

    public Optional<EjercicioModel> getById(Long id) {
        return ejercicioRepository.findById(id);
    }

    public EjercicioModel updateById(EjercicioModel request, Long id) {
        EjercicioModel ejercicio = ejercicioRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Ejercicio no encontrado")
        );

        ejercicio.setNombre_ejercicio(request.getNombre_ejercicio());
        ejercicio.setTipo_ejercicio(request.getTipo_ejercicio());
        ejercicio.setDescripcion_ejercicio(request.getDescripcion_ejercicio());
        // No actualizar imagen y video aquí
        return ejercicioRepository.save(ejercicio);
    }

    public Boolean deleteEjercicio(Long id) {
        try {
            ejercicioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateFileSize(long size) {
        final long MAX_SIZE = 64 * 1024 * 1024; // 64 MB como tamaño máximo
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("El archivo es demasiado grande");
        }
    }

    public Optional<byte[]> getImagenEjercicio(Long id) {
        return ejercicioRepository.findById(id).map(EjercicioModel::getImagen_ejercicio);
    }

    public Optional<byte[]> getVideoEjercicio(Long id) {
        return ejercicioRepository.findById(id).map(EjercicioModel::getVideo_ejercicio);
    }
}