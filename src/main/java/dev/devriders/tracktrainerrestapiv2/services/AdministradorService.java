package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.AdministradorModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IAdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {
    private final IAdministradorRepository administradorRepository;
    @Autowired
    public AdministradorService(IAdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }
    public List<AdministradorModel> getAllAdministradores() {
        return administradorRepository.findAll();
    }
    public AdministradorModel saveAdministrador(AdministradorModel administrador) {
        return administradorRepository.save(administrador);
    }
    public Optional<AdministradorModel> getAdministradorById(Long id) {
        return administradorRepository.findById(id);
    }
    public AdministradorModel updateAdministradorById(Long id, AdministradorModel request) {
        Optional<AdministradorModel> administradorOptional = administradorRepository.findById(id);
        if (administradorOptional.isPresent()) {
            AdministradorModel administrador = administradorOptional.get();
            administrador.setNombre(request.getNombre());
            administrador.setApellido(request.getApellido());
            administrador.setNickname(request.getNickname());
            administrador.setCorreo(request.getCorreo());
            administrador.setContraseña(request.getContraseña());
            return administradorRepository.save(administrador);
        }
        return null;
    }
    public boolean deleteAdministrador(Long id) {
        try {
            administradorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Optional<AdministradorModel> getAdministradorByCorreo(String correo) {
        return administradorRepository.findByCorreo(correo);
    }
}

