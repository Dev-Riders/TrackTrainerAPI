package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "Amigos")
public class AmigosModel {
    @Id
    @Column(name = "id_amigos")
    private Long idamigos;

    @Column(name = "estado")
    private String estado;

    @Column(name = "eliminacion")
    private String eliminacion;

    public AmigosModel(){

    }

    public AmigosModel(Long idamigos, String estado, String eliminacion) {
        this.idamigos = idamigos;
        this.estado = estado;
        this.eliminacion = eliminacion;
    }

    public Long getIdamigos() {
        return idamigos;
    }

    public void setIdamigos(Long idamigos) {
        this.idamigos = idamigos;
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
