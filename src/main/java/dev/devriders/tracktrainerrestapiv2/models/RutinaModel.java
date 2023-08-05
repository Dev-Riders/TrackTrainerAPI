package dev.devriders.tracktrainerrestapiv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Set;

import java.util.HashSet;

@Entity
@Table(name = "rutinas")
public class RutinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina")
    private Long idrutina;




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UsuarioModel usuario;

    @Column(name = "nombre_rutina")
    private String nombreRutina;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "ejexrut",
            joinColumns = { @JoinColumn(name = "id_rutina") },

            inverseJoinColumns = { @JoinColumn(name = "id_ejercicio") })
    private Set<EjercicioModel> ejercicios = new HashSet<>();

    public RutinaModel() {
    }
    public RutinaModel(Long idrutina, String nombreRutina, UsuarioModel usuario) {
        this.idrutina = idrutina;
        this.nombreRutina = nombreRutina;
        this.usuario = usuario;
    }

    public Long getIdrutina() {
        return idrutina;
    }

    public void setIdrutina(Long idRutina) {
        this.idrutina = idrutina;
    }



    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }


    public void addEjercicio(EjercicioModel ejercicio) {
        this.ejercicios.add(ejercicio);
        ejercicio.getRutina().add(this);
    }

    public void removeEjercicio(long id_ejercicio) {
        EjercicioModel ejercicio = this.ejercicios.stream().filter(t -> t.getIdejercicio() == id_ejercicio).findFirst().orElse(null);
        if (ejercicio != null) {
            this.ejercicios.remove(ejercicio);
            ejercicio.getRutina().remove(this);
        }
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }
}
