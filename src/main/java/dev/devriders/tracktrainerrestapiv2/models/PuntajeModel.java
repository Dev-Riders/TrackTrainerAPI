package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;

@Entity
@Table(name = "puntaje")
public class PuntajeModel {
    @Id
    @Column(name = "id_puntaje")
    private Long idPuntaje;

    @Column(name = "puntaje_mensual")
    private Long puntajeMensual;

    @Column(name = "puntaje_historico")
    private Long puntajeHistorico;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_usuario")
    private UsuarioModel usuarios;//Borrar

    public PuntajeModel(){
    }

    public PuntajeModel(Long idPuntaje, Long puntajeMensual, Long puntajeHistorico) {
        this.idPuntaje = idPuntaje;
        this.puntajeMensual = puntajeMensual;
        this.puntajeHistorico = puntajeHistorico;
    }

    public Long getIdPuntaje() {
        return idPuntaje;
    }

    public void setIdPuntaje(Long idPuntaje) {
        this.idPuntaje = idPuntaje;
    }

    public Long getPuntajeMensual() {
        return puntajeMensual;
    }

    public void setPuntajeMensual(Long puntajeMensual) {
        this.puntajeMensual = puntajeMensual;
    }

    public Long getPuntajeHistorico() {
        return puntajeHistorico;
    }

    public void setPuntajeHistorico(Long puntajeHistorico) {
        this.puntajeHistorico = puntajeHistorico;
    }


        //Borrar


    /*public UsuarioModel getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(UsuarioModel usuarios) {
        this.usuarios = usuarios;
    }*/
}
