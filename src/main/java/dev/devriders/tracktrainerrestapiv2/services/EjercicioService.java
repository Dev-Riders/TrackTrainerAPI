package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EjercicioService {
    @Autowired
    IEjercicioRepository ejercicioRepository;
    public ArrayList<EjercicioModel> getEjercicio() {
        return (ArrayList<EjercicioModel>) ejercicioRepository.findAll();
    }
    public EjercicioModel saveEjercicio(EjercicioModel ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }
    public Optional<EjercicioModel> getById(Long id) {
        return ejercicioRepository.findById(id);
    }
    public EjercicioModel updateById(EjercicioModel Request, Long id) {
        EjercicioModel ejercicio = ejercicioRepository.findById(id).get();
        ejercicio.setNombreEjercicio(Request.getNombreEjercicio());
        ejercicio.setTipoEjercicio(Request.getTipoEjercicio());
        return ejercicio;
    }
    public Boolean deleteEjercicio (Long id){
        try{
            ejercicioRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}