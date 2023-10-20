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

    public ArrayList<EjercicioModel> getEjercios(){
        return (ArrayList<EjercicioModel>) ejercicioRepository.findAll();
    }

    public EjercicioModel saveEjercicio (EjercicioModel ejercicio){
        return ejercicioRepository.save(ejercicio);
    }

    public Optional<EjercicioModel> getById(Long id){
        return ejercicioRepository.findById(id);
    }

    public EjercicioModel updateById(EjercicioModel request, Long id){
        EjercicioModel ejercicio = ejercicioRepository.findById(id).get();

        ejercicio.setNombre_ejercicio(request.getNombre_ejercicio());
        ejercicio.setTipo_ejercicio(request.getTipo_ejercicio());
        ejercicio.setImagen_ejercicio(request.getImagen_ejercicio());
        ejercicio.setVideo_ejercicio(request.getVideo_ejercicio());
        ejercicio.setDescripcion_ejercicio(request.getDescripcion_ejercicio());
        return ejercicio;
    }
    public Boolean deleteEjercicio(Long id){
        try{
            ejercicioRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
