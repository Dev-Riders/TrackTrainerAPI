package dev.devriders.tracktrainerrestapiv2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name= "ejercicios")
public class EjercicioModel {
  @Id
  @Column(name = "id_ejercicio")
  private Long idejercicio;

  @Column(name = "nombre_ejercicio")
  private String nombre_ejercicio;

  @Column(name = "tipo_ejercicio")
  private String tipo_ejercicio;
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          },
          mappedBy = "ejercicios")
  @JsonIgnore
  private Set<CategoriaModel> categorias = new HashSet<>();
  public EjercicioModel() {

  }

  public EjercicioModel(Long id_ejercicio, String nombre_ejercicio, String tipo_ejercicio) {
    this.idejercicio = id_ejercicio;
    this.nombre_ejercicio = nombre_ejercicio;
    this.tipo_ejercicio = tipo_ejercicio;
  }

  public Long getId_ejercicio() {
    return idejercicio;
  }

  public void setId_ejercicio(Long id_ejercicio) {
    this.idejercicio = id_ejercicio;
  }

  public String getNombre_ejercicio() {
    return nombre_ejercicio;
  }

  public void setNombre_ejercicio(String nombre_ejercicio) {
    this.nombre_ejercicio = nombre_ejercicio;
  }

  public String getTipo_ejercicio() {
    return tipo_ejercicio;
  }

  public void setTipo_ejercicio(String tipo_ejercicio) {
    this.tipo_ejercicio = tipo_ejercicio;
  }
  //Declaraciones funciones many to many
  public Set<CategoriaModel> getCategorias() {
    return categorias;
  }
}
