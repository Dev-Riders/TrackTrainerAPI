package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.EjercicioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEjercicioRepository extends JpaRepository<EjercicioModel, Long> {
    List<EjercicioModel> findEjerciciosByCategoriasIdcategoria(Long categoriaId);
}
