package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.AmigosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAmigosRepository extends JpaRepository<AmigosModel, Long> {

    List<AmigosModel> findByusuarioId(Long postId);

}
