package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "categorias")
public class CategoriaModel {
    @Id
    @Column(name = "id_categoria")
    private Long id_categoria;

    @Column(name = "Nombre_categoria")
    private String nombre_categoria;

    public CategoriaModel(){
    }

    public CategoriaModel(Long id_categoria, String nombre_categoria) {
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
