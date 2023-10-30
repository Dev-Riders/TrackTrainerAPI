package dev.devriders.tracktrainerrestapiv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "misiones")
public class MisionModel {

    @Id
    @Column(name = "id_mision")
    private Long idmision;
    @Column(name = "nombre_mision")
    private String nombreMision;
    @Column(name = "Descripcion_mision")
    private String descripcionMision;

    @Column(name = "puntaje_mision")
    private int puntaje;



    //Declaracion many to many
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "misiones")
    @JsonIgnore
    private Set<UsuarioModel> usuarios = new HashSet<>();
    //Fin declaracion


    public MisionModel() {
    }
    public MisionModel(Long idmision, String nombreMision, String descripcionMision, int puntaje) {
        this.idmision = idmision;
        this.nombreMision = nombreMision;
        this.descripcionMision = descripcionMision;
        this.puntaje = puntaje;
    }

    public Long getIdmision() {
        return idmision;
    }

    public void setIdmision(Long idmision) {
        this.idmision = idmision;
    }

    public String getNombreMision() {
        return nombreMision;
    }

    public void setNombreMision(String nombreMision) {
        this.nombreMision = nombreMision;
    }

    public String getDescripcionMision() {
        return descripcionMision;
    }

    public void setDescripcionMision(String descripcionMision) {
        this.descripcionMision = descripcionMision;
    }
    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    //Declaraciones funciones many to many
    public Set<UsuarioModel> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<UsuarioModel> usuarios) {
        this.usuarios = usuarios;
    }
    //fin declaraciones
}
