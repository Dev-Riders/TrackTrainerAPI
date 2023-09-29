package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.InfoUsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IInfoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InfoUsuarioService {
    private final IInfoUsuarioRepository infoUsuarioRepository;
    @Autowired
    public InfoUsuarioService(IInfoUsuarioRepository infoUsuarioRepository) {
        this.infoUsuarioRepository = infoUsuarioRepository;
    }
    public List<InfoUsuarioModel> getAllInfoUsuarios() {
        return infoUsuarioRepository.findAll();
    }
    public InfoUsuarioModel saveInfoUsuario(InfoUsuarioModel infoUsuario) {
        return infoUsuarioRepository.save(infoUsuario);
    }
    public Optional<InfoUsuarioModel> getInfoUsuarioById(Long usuarioId) {
        return infoUsuarioRepository.findById(usuarioId);
    }
    public InfoUsuarioModel updateInfoUsuarioById(Long usuarioId, InfoUsuarioModel request) {
        Optional<InfoUsuarioModel> infoUsuarioOptional = infoUsuarioRepository.findById(usuarioId);
        if (infoUsuarioOptional.isPresent()) {
            InfoUsuarioModel infoUsuario = infoUsuarioOptional.get();
            infoUsuario.setFechaNacimiento(request.getFechaNacimiento());
            infoUsuario.setPeso(request.getPeso());
            infoUsuario.setEstatura(request.getEstatura());
            infoUsuario.setGenero(request.getGenero());
            return infoUsuarioRepository.save(infoUsuario);
        }
        return null;
    }
    public Float IMC(Long usuarioId) {
        Float imc,peso,estatura;
        InfoUsuarioModel request = infoUsuarioRepository.findById(usuarioId).get();
        peso = request.getPeso().floatValue();
        estatura = request.getEstatura().floatValue()/100;
        imc = peso/(estatura*estatura);
        return imc;
    }
    public boolean deleteInfoUsuario(Long usuarioId) {
        try {
            infoUsuarioRepository.deleteById(usuarioId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Optional<InfoUsuarioModel> getById(Long id) {
        return infoUsuarioRepository.findById(id);
    }

}
