package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.MisionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMisionRepository extends JpaRepository<MisionModel, Long> {
    List<MisionModel> findMisionesByUsuariosId(Long Id);
}
