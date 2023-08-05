package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "administradores")
public class AdministradorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column(unique = true)
    private String nickname;
    @Column(unique = true)
    private String correo;
    @Column
    private String contraseña;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "reset_code")
    private String resetCode;
    public AdministradorModel() {
    }
    public AdministradorModel(Long id, String nombre, String apellido, String nickname, String correo, String contraseña, LocalDateTime fechaCreacion, String resetCode) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.correo = correo;
        this.contraseña = contraseña;
        this.fechaCreacion = fechaCreacion;
        this.resetCode = resetCode;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getResetCode() {
        return resetCode;
    }
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }
}
