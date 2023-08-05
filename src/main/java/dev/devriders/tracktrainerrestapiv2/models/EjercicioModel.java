package dev.devriders.tracktrainerrestapiv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ejercicios")
public class EjercicioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Long idejercicio;
    @Column(name = "nombre_ejercicio")
    private String nombreEjercicio;
    @Column(name = "tipo_ejercicio")
    private String tipoEjercicio;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "ejercicios")
    @JsonIgnore
    private Set<RutinaModel> rutinas = new HashSet<>();
    public EjercicioModel() {
    }
    public EjercicioModel(Long idejercicio, String nombreEjercicio, String tipoEjercicio) {
        this.idejercicio = idejercicio;
        this.nombreEjercicio = nombreEjercicio;
        this.tipoEjercicio = tipoEjercicio;
    }
    public Long getIdejercicio() {
        return idejercicio;
    }
    public void setIdejercicio(Long idejercicio) {
        this.idejercicio = idejercicio;
    }
    public String getNombreEjercicio() {
        return nombreEjercicio;
    }
    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }
    public String getTipoEjercicio() {
        return tipoEjercicio;
    }
    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public Set<RutinaModel> getRutina() {
        return rutinas;
    }

    public void setRutina(Set<RutinaModel> rutinas) {
        this.rutinas = rutinas;
    }
}
