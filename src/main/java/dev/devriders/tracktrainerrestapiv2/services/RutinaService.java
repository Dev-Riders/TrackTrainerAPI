package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.RutinaModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IRutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RutinaService {
    @Autowired
    IRutinaRepository rutinaRepository;

    public ArrayList<RutinaModel> getRutina() {
        return (ArrayList<RutinaModel>) rutinaRepository.findAll();
    }

    public RutinaModel saveRutina(RutinaModel rutina) {
        return rutinaRepository.save(rutina);
    }

    public Optional<RutinaModel> getById(Long id) {
        return rutinaRepository.findById(id);
    }

    public RutinaModel updateById(RutinaModel Request, Long id) {
        RutinaModel rutina = rutinaRepository.findById(id).get();

        rutina.setNombreRutina(Request.getNombreRutina());
        return rutina;
    }

    public Boolean deleteRutina (Long id){
        try{
            rutinaRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
