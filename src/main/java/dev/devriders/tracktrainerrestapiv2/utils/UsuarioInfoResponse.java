package dev.devriders.tracktrainerrestapiv2.utils;

import dev.devriders.tracktrainerrestapiv2.models.InfoUsuarioModel;
import dev.devriders.tracktrainerrestapiv2.models.UsuarioModel;

public class UsuarioInfoResponse {
    private UsuarioModel usuario;
    private InfoUsuarioModel infoUsuario;

    public UsuarioInfoResponse(UsuarioModel usuario, InfoUsuarioModel infoUsuario) {
        this.usuario = usuario;
        this.infoUsuario = infoUsuario;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public InfoUsuarioModel getInfoUsuario() {
        return infoUsuario;
    }

    public void setInfoUsuario(InfoUsuarioModel infoUsuario) {
        this.infoUsuario = infoUsuario;
    }
}
