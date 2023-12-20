package dev.devriders.tracktrainerrestapiv2.repositories;

import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByNickname(String nickname);
    Optional<UsuarioModel> findByCorreo(String correo);
    Optional<UsuarioModel> findByVerificationCode(String verificationCode);
    Optional<UsuarioModel> findByResetCode(String resetCode);
    List<UsuarioModel> findByNicknameLike(String nickname);
    Page<UsuarioModel> findByCorreoAndNickname(String correo, String nickname, Pageable pageable);
    Page<UsuarioModel> findByCorreo(String correo, Pageable pageable);
    Page<UsuarioModel> findByNickname(String nickname, Pageable pageable);

    //Listado para funciones many to many
    List<UsuarioModel> findUsuarioByMisionesIdmision(Long Id_mision);

    List<UsuarioModel> findUsuarioByLogrosIdlogro(Long tagId);
    //fin listado
}
