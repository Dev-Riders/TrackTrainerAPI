package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.PuntajeModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IPuntajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PuntajeService {
    @Autowired
    IPuntajeRepository puntajeRepository;

    public ArrayList <PuntajeModel> getPuntajes(){
        return (ArrayList<PuntajeModel>) puntajeRepository.findAll();
    }

    public PuntajeModel savePuntaje(PuntajeModel puntaje){
        return puntajeRepository.save(puntaje);
    }

    public Optional<PuntajeModel> getById (Long id){
        return puntajeRepository.findById(id);
    }

    public PuntajeModel updateById (PuntajeModel request, Long id){
        PuntajeModel puntaje = puntajeRepository.findById(id).get();

        puntaje.setPuntajeHistorico(request.getPuntajeHistorico());
        puntaje.setPuntajeMensual(request.getPuntajeMensual());
        puntaje.setUsuariosid(request.getUsuariosid());//Borrar

        return puntaje;
    }

    public Boolean deletePuntaje (Long id){
        try{
            puntajeRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
