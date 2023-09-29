package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Long> {
}