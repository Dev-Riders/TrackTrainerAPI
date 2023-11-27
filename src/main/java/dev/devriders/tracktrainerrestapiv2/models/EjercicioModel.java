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

  @Lob
  @Column(name = "imagen_ejercicio", columnDefinition = "LONGBLOB")
  private byte[] imagen_ejercicio;

  @Lob
  @Column(name = "video_ejercicio", columnDefinition = "LONGBLOB")
  private byte[] video_ejercicio;

  @Column(name = "descripcion_ejercicio")
  private String descripcion_ejercicio;

  // Constructores, Getters y Setters
  public EjercicioModel() {

  }

  public EjercicioModel(Long id_ejercicio, String nombre_ejercicio, String tipo_ejercicio, byte[] imagen_ejercicio, byte[] video_ejercicio, String descripcion_ejercicio) {
    this.idejercicio = id_ejercicio;
    this.nombre_ejercicio = nombre_ejercicio;
    this.tipo_ejercicio = tipo_ejercicio;
    this.imagen_ejercicio = imagen_ejercicio;
    this.video_ejercicio = video_ejercicio;
    this.descripcion_ejercicio = descripcion_ejercicio;
  }

  // Resto de getters y setters
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

  public byte[] getImagen_ejercicio() {
    return imagen_ejercicio;
  }

  public void setImagen_ejercicio(byte[] imagen_ejercicio) {
    this.imagen_ejercicio = imagen_ejercicio;
  }

  public byte[] getVideo_ejercicio() {
    return video_ejercicio;
  }

  public void setVideo_ejercicio(byte[] video_ejercicio) {
    this.video_ejercicio = video_ejercicio;
  }

  public String getDescripcion_ejercicio() {
    return descripcion_ejercicio;
  }

  public void setDescripcion_ejercicio(String descripcion_ejercicio) {
    this.descripcion_ejercicio = descripcion_ejercicio;
  }

  // Funciones de la relación Many-to-Many
  public Set<CategoriaModel> getCategorias() {
    return categorias;
  }
}