package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import dev.devriders.tracktrainerrestapiv2.repositories.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    ICategoriaRepository categoriaRepository;

    public ArrayList<CategoriaModel> getCategorias(){
        return (ArrayList<CategoriaModel>) categoriaRepository.findAll();
    }

    public CategoriaModel saveCategoria (CategoriaModel categoria){
        return categoriaRepository.save(categoria);
    }

    public Optional<CategoriaModel> getById(Long id){
        return categoriaRepository.findById(id);
    }

    public CategoriaModel updateById(CategoriaModel request, Long id){
        CategoriaModel categoria = categoriaRepository.findById(id).get();

        categoria.setNombre_categoria(request.getNombre_categoria());
        return categoria;
    }
    public Boolean deleteCategoria(Long id){
        try{
            categoriaRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
