package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.EjexuserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IEjexuserRepository extends JpaRepository<EjexuserModel, Long> {
    Optional<ArrayList<EjexuserModel>> findByIdusuario(Long id);
}
