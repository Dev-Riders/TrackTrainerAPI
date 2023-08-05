package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "info_usuario")
public class InfoUsuarioModel {
    @Id
    @Column(name = "usuario_id")
    private Long usuarioId;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column
    private BigDecimal peso;
    @Column
    private BigDecimal estatura;
    @Column
    private String genero;
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public BigDecimal getPeso() {
        return peso;
    }
    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }
    public BigDecimal getEstatura() {
        return estatura;
    }
    public void setEstatura(BigDecimal estatura) {
        this.estatura = estatura;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
}
