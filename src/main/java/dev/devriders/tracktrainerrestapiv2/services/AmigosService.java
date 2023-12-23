package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.AmigosModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IAmigosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AmigosService {

    @Autowired
    IAmigosRepository amigosRepository;

    public ArrayList<AmigosModel> getAmigos(){
        return (ArrayList<AmigosModel>)  amigosRepository.findAll();
    }

    public AmigosModel saveAmigo(AmigosModel amigo){
        return amigosRepository.save(amigo);
    }

    public Optional<AmigosModel> getById(Long id){
        return amigosRepository.findById(id);
    }

    public AmigosModel UpdateById(AmigosModel request, Long id){
        AmigosModel amigos = amigosRepository.findById(id).get();

        amigos.setEstado(request.getEstado());
        amigos.setEliminacion(request.getEliminacion());

        return amigos;
    }

    public Boolean deleteUser(Long id){
        try{
            amigosRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
