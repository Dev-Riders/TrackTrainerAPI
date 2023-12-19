package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;



@Entity
@Table (name = "ejexuser")
public class EjexuserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ejexuser;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "Id_ejercicio", nullable = false)
    private EjercicioModel ejercicio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column (name = "tiempo")
    private Integer tiempo;

    @Column (name = "peso")
    private Integer peso;

    public EjexuserModel() {

    }

    public EjexuserModel(Long id_ejexuser, UsuarioModel usuario, EjercicioModel ejercicio, Integer cantidad, Integer tiempo, Integer peso) {
        this.id_ejexuser = id_ejexuser;
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.cantidad = cantidad;
        this.tiempo = tiempo;
        this.peso = peso;
    }

    public Long getId_ejexuser() {
        return id_ejexuser;
    }

    public void setId_ejexuser(Long id_ejexuser) {
        this.id_ejexuser = id_ejexuser;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public EjercicioModel getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(EjercicioModel ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }
}
