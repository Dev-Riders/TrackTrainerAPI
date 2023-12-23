package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EjercicioService {

    @Autowired
    private IEjercicioRepository ejercicioRepository;

    private final String uploadDir = "src/main/resources/static/uploads/ejercicios/";

    public ArrayList<EjercicioModel> getEjercicios() {
        return (ArrayList<EjercicioModel>) ejercicioRepository.findAll();
    }

    public EjercicioModel saveEjercicio(EjercicioModel ejercicio, MultipartFile imagenFile, MultipartFile videoFile) {
        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                validateFileSize(imagenFile.getSize());
                String imagenPath = storeFile(imagenFile, ejercicio.getNombreEjercicio(), "imagenes");
                ejercicio.setImagenEjercicio(imagenPath);
            }
            if (videoFile != null && !videoFile.isEmpty()) {
                validateFileSize(videoFile.getSize());
                String videoPath = storeFile(videoFile, ejercicio.getNombreEjercicio(), "videos");
                ejercicio.setVideoEjercicio(videoPath);
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

        ejercicio.setNombreEjercicio(request.getNombreEjercicio());
        ejercicio.setDescripcionEjercicio(request.getDescripcionEjercicio());
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

    private String storeFile(MultipartFile file, String ejercicioNombre, String subDir) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String filename = ejercicioNombre.replaceAll("\\s+", "_") + fileExtension;
        Path fullPath = Paths.get(uploadDir + subDir + File.separator + filename);
        Files.createDirectories(fullPath.getParent());
        Files.copy(file.getInputStream(), fullPath);

        return "/uploads/ejercicios/" + subDir + "/" + filename;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public EjercicioModel updateEjercicio(EjercicioModel request, Long id, MultipartFile imagenFile, MultipartFile videoFile) {
        EjercicioModel ejercicio = getById(id).orElseThrow(
                () -> new RuntimeException("Ejercicio no encontrado")
        );

        // Actualización condicional de campos
        if (request.getNombreEjercicio() != null) {
            ejercicio.setNombreEjercicio(request.getNombreEjercicio());
        }
        if (request.getDescripcionEjercicio() != null) {
            ejercicio.setDescripcionEjercicio(request.getDescripcionEjercicio());
        }

        try {
            // Actualización de imagen si se proporciona una nueva y no existe una previa
            if (imagenFile != null && !imagenFile.isEmpty() && (ejercicio.getImagenEjercicio() == null || ejercicio.getImagenEjercicio().isEmpty())) {
                validateFileSize(imagenFile.getSize());
                String imagenPath = storeFile(imagenFile, ejercicio.getNombreEjercicio(), "imagenes");
                ejercicio.setImagenEjercicio(imagenPath);
            }

            // Actualización de video si se proporciona uno nuevo y no existe uno previo
            if (videoFile != null && !videoFile.isEmpty() && (ejercicio.getVideoEjercicio() == null || ejercicio.getVideoEjercicio().isEmpty())) {
                validateFileSize(videoFile.getSize());
                String videoPath = storeFile(videoFile, ejercicio.getNombreEjercicio(), "videos");
                ejercicio.setVideoEjercicio(videoPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al procesar archivo: " + e.getMessage());
        }

        return ejercicioRepository.save(ejercicio);
    }
}