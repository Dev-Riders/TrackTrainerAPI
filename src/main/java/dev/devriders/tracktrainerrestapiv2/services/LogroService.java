package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.LogroModel;
import dev.devriders.tracktrainerrestapiv2.repositories.ILogroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LogroService {

    @Autowired
    ILogroRepository logroRepository;
    public ArrayList<LogroModel> getLogro() {
        return (ArrayList<LogroModel>) logroRepository.findAll();
    }
    public LogroModel saveLogro(LogroModel logro) {
        return logroRepository.save(logro);
    }
    public Optional<LogroModel> getById(Long id) {
        return logroRepository.findById(id);
    }
    public LogroModel updateById(LogroModel Request, Long id) {
        LogroModel logro = logroRepository.findById(id).get();
        logro.setNombreLogro(Request.getNombreLogro());
        logro.setDescripcionLogro(Request.getDescripcionLogro());
        return logro;
    }
    public Boolean deleteLogro (Long id){
        try{
            logroRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
