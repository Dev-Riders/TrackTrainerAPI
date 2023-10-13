package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table (name = "ejexuser")
public class EjexuserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ejexuser;

    @Column(name = "id_ejercicio")
    private Long id_ejercicio;

    @Column(name = "id_usuario")
    private Long idusuario;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column (name = "tiempo")
    private Integer tiempo;

    @Column (name = "peso")
    private Integer peso;

    public EjexuserModel() {

    }
    public EjexuserModel(Long id_ejexuser, Long id_ejercicio, Long idusuario, Integer cantidad, Integer tiempo, Integer peso) {
        this.id_ejexuser = id_ejexuser;
        this.id_ejercicio = id_ejercicio;
        this.idusuario = idusuario;
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

    public Long getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(Long id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public Long getId_usuario() {
        return idusuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.idusuario = id_usuario;
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
