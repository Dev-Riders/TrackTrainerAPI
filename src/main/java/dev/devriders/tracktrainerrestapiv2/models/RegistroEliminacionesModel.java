package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_eliminaciones")
public class RegistroEliminacionesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
    @ManyToOne
    @JoinColumn(name = "quien_elimino")
    private AdministradorModel quienElimino;
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;
    @Column
    private String motivo;
    public RegistroEliminacionesModel() {
    }
    public RegistroEliminacionesModel(Long id, UsuarioModel usuario, AdministradorModel quienElimino, LocalDateTime fechaEliminacion, String motivo) {
        this.id = id;
        this.usuario = usuario;
        this.quienElimino = quienElimino;
        this.fechaEliminacion = fechaEliminacion;
        this.motivo = motivo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UsuarioModel getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }
    public AdministradorModel getQuienElimino() {
        return quienElimino;
    }
    public void setQuienElimino(AdministradorModel quienElimino) {
        this.quienElimino = quienElimino;
    }
    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }
    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
