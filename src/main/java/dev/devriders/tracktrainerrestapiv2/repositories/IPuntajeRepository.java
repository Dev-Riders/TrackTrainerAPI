package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.PuntajeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPuntajeRepository extends JpaRepository<PuntajeModel, Long> {
    List<PuntajeModel> findPuntajesByUsuariosid(Long Id);
}
