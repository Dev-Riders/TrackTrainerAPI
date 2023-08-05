package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.LogroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILogroRepository  extends JpaRepository<LogroModel, Long> {
    List<LogroModel> findLogrosByUsuariosId(Long Id);
}
