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

    private final String uploadDir = "uploads/ejercicios/";

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
        Path storagePath = Paths.get(uploadDir + subDir + File.separator + filename);
        Files.createDirectories(storagePath.getParent());
        Files.copy(file.getInputStream(), storagePath);

        return storagePath.toString();
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}