package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IMisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MisionService {
    @Autowired
    IMisionRepository misionRepository;
    public ArrayList<MisionModel> getMision() {
        return (ArrayList<MisionModel>) misionRepository.findAll();
    }
    public MisionModel saveMision(MisionModel mision) {
        return misionRepository.save(mision);
    }
    public Optional<MisionModel> getById(Long id) {
        return misionRepository.findById(id);
    }
    public MisionModel updateById(MisionModel Request, Long id) {
        MisionModel mision = misionRepository.findById(id).get();
        mision.setNombreMision(Request.getNombreMision());
        mision.setDescripcionMision(Request.getDescripcionMision());
        mision.setPuntaje(Request.getPuntaje());
        return mision;
    }
    public Boolean deleteMision (Long id){
        try{
            misionRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
