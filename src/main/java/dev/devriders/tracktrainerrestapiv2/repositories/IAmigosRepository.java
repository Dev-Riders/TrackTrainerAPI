package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.AmigosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAmigosRepository extends JpaRepository<AmigosModel, Long> {


}
