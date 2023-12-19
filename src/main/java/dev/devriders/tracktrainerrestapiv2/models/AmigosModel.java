package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;


@Entity
@Table(name= "Amigos")
public class AmigosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amistad")
    private Long idamistad;

    @Column(name = "estado")
    private String estado;

    @Column(name = "eliminacion")
    private String eliminacion;

    @ManyToOne//(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private UsuarioModel usuario;

    @ManyToOne//(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_amigo", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    private UsuarioModel amigo;

    public AmigosModel(){

    }

    public AmigosModel(Long idamigos, String estado, String eliminacion, UsuarioModel usuario, UsuarioModel amigo) {
        this.idamistad = idamigos;
        this.estado = estado;
        this.eliminacion = eliminacion;
        this.usuario = usuario;
        this.amigo = amigo;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public UsuarioModel getAmigo() {
        return amigo;
    }

    public void setAmigo(UsuarioModel amigo) {
        this.amigo = amigo;
    }

    public Long getIdamigos() {
        return idamistad;
    }

    public void setIdamigos(Long idamigos) {
        this.idamistad = idamigos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEliminacion() {
        return eliminacion;
    }

    public void setEliminacion(String eliminacion) {
        this.eliminacion = eliminacion;
    }
}
