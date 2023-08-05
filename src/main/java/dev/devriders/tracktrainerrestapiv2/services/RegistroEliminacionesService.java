package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.RegistroEliminacionesModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IRegistroEliminacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroEliminacionesService {
    private final IRegistroEliminacionesRepository registroEliminacionesRepository;
    @Autowired
    public RegistroEliminacionesService(IRegistroEliminacionesRepository registroEliminacionesRepository) {
        this.registroEliminacionesRepository = registroEliminacionesRepository;
    }
    public List<RegistroEliminacionesModel> getAllRegistrosEliminaciones() {
        return registroEliminacionesRepository.findAll();
    }
    public RegistroEliminacionesModel saveRegistroEliminaciones(RegistroEliminacionesModel registroEliminaciones) {
        return registroEliminacionesRepository.save(registroEliminaciones);
    }
    public Optional<RegistroEliminacionesModel> getRegistroEliminacionesById(Long id) {
        return registroEliminacionesRepository.findById(id);
    }
    public RegistroEliminacionesModel updateRegistroEliminacionesById(Long id, RegistroEliminacionesModel request) {
        Optional<RegistroEliminacionesModel> registroEliminacionesOptional = registroEliminacionesRepository.findById(id);
        if (registroEliminacionesOptional.isPresent()) {
            RegistroEliminacionesModel registroEliminaciones = registroEliminacionesOptional.get();
            registroEliminaciones.setUsuario(request.getUsuario());
            registroEliminaciones.setQuienElimino(request.getQuienElimino());
            registroEliminaciones.setFechaEliminacion(request.getFechaEliminacion());
            registroEliminaciones.setMotivo(request.getMotivo());
            return registroEliminacionesRepository.save(registroEliminaciones);
        }
        return null;
    }
    public boolean deleteRegistroEliminaciones(Long id) {
        try {
            registroEliminacionesRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

