package dev.devriders.tracktrainerrestapiv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "logros")
public class LogroModel {

    @Id
    @Column(name = "id_logro")
    private Long idlogro;
    @Column(name = "nombre_logro")
    private String nombreLogro;
    @Column(name = "descripcion_Logro")
    private String descripcionLogro;

    //Declaracion many to many
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "logros")
    @JsonIgnore
    private Set<UsuarioModel> usuarios = new HashSet<>();
    //Fin declaracion

    public LogroModel() {
    }
    public LogroModel(Long idlogro, String nombreLogro, String descripcionLogro) {
        this.idlogro = idlogro;
        this.nombreLogro = nombreLogro;
        this.descripcionLogro = descripcionLogro;
    }

    public Long getIdlogro() {
        return idlogro;
    }

    public void setIdlogro(Long idlogro) {
        this.idlogro = idlogro;
    }

    public String getNombreLogro() {
        return nombreLogro;
    }

    public void setNombreLogro(String nombreLogro) {
        this.nombreLogro = nombreLogro;
    }

    public String getDescripcionLogro() {
        return descripcionLogro;
    }

    public void setDescripcionLogro(String descripcionLogro) {
        this.descripcionLogro = descripcionLogro;
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
