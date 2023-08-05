package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.RegistroEliminacionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroEliminacionesRepository extends JpaRepository<RegistroEliminacionesModel, Long> {
}
