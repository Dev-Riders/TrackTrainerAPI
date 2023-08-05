package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.RutinaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRutinaRepository extends JpaRepository<RutinaModel, Long> {
    List<RutinaModel> findRutinaByEjerciciosIdejercicio(Long id_ejercicio);

    List<RutinaModel> findByUsuario_Id(Long id);

    //@Transactional
    //void deleteByRutinasIdrutina(long id_rutina);
}
