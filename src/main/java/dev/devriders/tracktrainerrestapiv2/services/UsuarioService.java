package dev.devriders.tracktrainerrestapiv2.services;

import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;
import dev.devriders.tracktrainerrestapiv2.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final IUsuarioRepository usuarioRepository;
    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public UsuarioModel saveUsuario(UsuarioModel usuario) {
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }
    public Optional<UsuarioModel> getById(Long id) {
        return usuarioRepository.findById(id);
    }
    public UsuarioModel updateUsuarioById(UsuarioModel request, Long id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setNickname(request.getNickname());
            usuario.setCorreo(request.getCorreo());
            usuario.setContraseña(request.getContraseña());
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    public boolean deleteUsuario(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //getAllUsers
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }
    public Page<UsuarioModel> getUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Page<UsuarioModel> findByCorreoAndNickname(String correo, String nickname, Pageable pageable) {
        return usuarioRepository.findByCorreoAndNickname(correo, nickname, pageable);
    }

    public Page<UsuarioModel> findByCorreo(String correo, Pageable pageable) {
        return usuarioRepository.findByCorreo(correo, pageable);
    }

    public Page<UsuarioModel> findByNickname(String nickname, Pageable pageable) {
        return usuarioRepository.findByNickname(nickname, pageable);
    }

}

