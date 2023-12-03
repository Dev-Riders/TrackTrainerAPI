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

  @Column(name = "imagen_ejercicio")
  private String imagenEjercicio; // Almacenar la ruta de la imagen

  @Column(name = "video_ejercicio")
  private String videoEjercicio; // Almacenar la ruta del video

  @Column(name = "descripcion_ejercicio")
  private String descripcionEjercicio;

  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
                  CascadeType.PERSIST,
                  CascadeType.MERGE
          },
          mappedBy = "ejercicios")
  @JsonIgnore
  private Set<CategoriaModel> categorias = new HashSet<>();

  // Constructores
  public EjercicioModel() {
  }

  public EjercicioModel(Long idEjercicio, String nombreEjercicio, String imagenEjercicio, String videoEjercicio, String descripcionEjercicio) {
    this.idejercicio = idEjercicio;
    this.nombreEjercicio = nombreEjercicio;
    this.imagenEjercicio = imagenEjercicio;
    this.videoEjercicio = videoEjercicio;
    this.descripcionEjercicio = descripcionEjercicio;
  }

  // Getters y setters
  public Long getIdEjercicio() {
    return idejercicio;
  }

  public void setIdEjercicio(Long idEjercicio) {
    this.idejercicio = idEjercicio;
  }

  public String getNombreEjercicio() {
    return nombreEjercicio;
  }

  public void setNombreEjercicio(String nombreEjercicio) {
    this.nombreEjercicio = nombreEjercicio;
  }


  public String getImagenEjercicio() {
    return imagenEjercicio;
  }

  public void setImagenEjercicio(String imagenEjercicio) {
    this.imagenEjercicio = imagenEjercicio;
  }

  public String getVideoEjercicio() {
    return videoEjercicio;
  }

  public void setVideoEjercicio(String videoEjercicio) {
    this.videoEjercicio = videoEjercicio;
  }

  public String getDescripcionEjercicio() {
    return descripcionEjercicio;
  }

  public void setDescripcionEjercicio(String descripcionEjercicio) {
    this.descripcionEjercicio = descripcionEjercicio;
  }

  // Funciones de la relación Many-to-Many
  public Set<CategoriaModel> getCategorias() {
    return categorias;
  }

  public void setCategorias(Set<CategoriaModel> categorias) {
    this.categorias = categorias;
  }
}