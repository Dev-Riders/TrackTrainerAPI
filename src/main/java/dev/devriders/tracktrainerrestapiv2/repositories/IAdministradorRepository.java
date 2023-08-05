package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.AdministradorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IAdministradorRepository extends JpaRepository<AdministradorModel, Long> {
    Optional<AdministradorModel> findByNickname(String nickname);
    Optional<AdministradorModel> findByCorreo(String correo);
    Optional<AdministradorModel> findByResetCode(String resetCode);
}
