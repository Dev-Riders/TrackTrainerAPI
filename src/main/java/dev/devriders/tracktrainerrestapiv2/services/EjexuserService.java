package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.EjexuserModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IEjexuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EjexuserService {

    @Autowired
    IEjexuserRepository ejexuserRepository;

    public ArrayList <EjexuserModel> getEjexusers(){
        return (ArrayList<EjexuserModel>) ejexuserRepository.findAll();
    }

    public EjexuserModel saveEjexuser(EjexuserModel ejexuser){
        return ejexuserRepository.save(ejexuser);
    }

    public Optional<EjexuserModel> getById(Long id){
        return ejexuserRepository.findById(id);
    }

   /* public Optional<ArrayList<EjexuserModel>> getByUserId(Long id){
        return ejexuserRepository.findByIdusuario(id);

    }*/
    public EjexuserModel updateById(EjexuserModel request, Long id){
        EjexuserModel ejexuser = ejexuserRepository.findById(id).get();
        //ejexuser.setId_usuario(request.getId_usuario());
        //ejexuser.setId_ejercicio(request.getId_ejercicio());
        ejexuser.setCantidad(request.getCantidad());
        ejexuser.setTiempo(request.getTiempo());
        ejexuser.setPeso(request.getPeso());
        return ejexuser;
    }

    public Boolean deleteEjexuser (Long id){
        try{
            ejexuserRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
