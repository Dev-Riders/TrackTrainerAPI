package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "categorias")
public class CategoriaModel {
    @Id
    @Column(name = "id_categoria")
    private Long idcategoria;

    @Column(name = "Nombre_categoria")
    private String nombre_categoria;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "ejexcat",
            joinColumns = { @JoinColumn(name = "id_categoria") },
            inverseJoinColumns = { @JoinColumn(name = "id_ejercicio") })
    private Set<EjercicioModel> ejercicios = new HashSet<>();
    public CategoriaModel(){
    }

    public CategoriaModel(Long id_categoria, String nombre_categoria) {
        this.idcategoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
    }
    //Funciones para tablas many to many
    public void addEjercicio(EjercicioModel ejercicio) {
        this.ejercicios.add(ejercicio);
        ejercicio.getCategorias().add(this);
    }
    public void removeEjercicio(long ejercicioId) {
        EjercicioModel ejercicio = this.ejercicios.stream().filter(t -> t.getIdEjercicio() == ejercicioId).findFirst().orElse(null);
        if (ejercicio != null) {
            this.ejercicios.remove(ejercicio);
            ejercicio.getCategorias().remove(this);
        }
    }
    public Long getId_categoria() {
        return idcategoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.idcategoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
