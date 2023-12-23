package dev.devriders.tracktrainerrestapiv2.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column (name="id")
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
    @Column(name = "verification_code")
    private String verificationCode;
    @Column
    private boolean verified;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "reset_code")
    private String resetCode;
    @Column(name = "intentos_fallidos")
    private int intentosFallidos;
    @Column
    private boolean bloqueado;
    @Column
    private boolean eliminado;
    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;
    @ManyToOne
    @JoinColumn(name = "quien_elimino")
    private AdministradorModel quienElimino;
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "suscrito")
    private boolean suscrito = false;
    @ManyToOne
    @JoinColumn(name = "quien_actualizo")
    private AdministradorModel quienActualizo;

    public UsuarioModel() {
    }

    //Adición campos para misiones y logros
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "mision_user",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn(name = "id_mision") })
    private Set<MisionModel> misiones = new HashSet<>();
    /*@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "ejercicio_user",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn(name = "id_ejercicio") })
    private Set<EjercicioModel> ejercicio = new HashSet<>();*/
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "logro_user",
            joinColumns = { @JoinColumn(name = "id_usuario") },
            inverseJoinColumns = { @JoinColumn(name = "id_logro") })
    private Set<LogroModel> logros = new HashSet<>();
    //Fin campos
    public UsuarioModel(Long id, String nombre, String apellido, String nickname, String correo, String contraseña, String verificationCode, boolean verified, LocalDateTime fechaCreacion, String resetCode, int intentosFallidos, boolean bloqueado, boolean eliminado, LocalDateTime fechaEliminacion, AdministradorModel quienElimino, LocalDateTime fechaActualizacion, AdministradorModel quienActualizo, boolean suscrito) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.correo = correo;
        this.contraseña = contraseña;
        this.verificationCode = verificationCode;
        this.verified = verified;
        this.fechaCreacion = fechaCreacion;
        this.resetCode = resetCode;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
        this.eliminado = eliminado;
        this.fechaEliminacion = fechaEliminacion;
        this.quienElimino = quienElimino;
        this.fechaActualizacion = fechaActualizacion;
        this.quienActualizo = quienActualizo;
        this.suscrito = suscrito;
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
    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
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
    public int getIntentosFallidos() {
        return intentosFallidos;
    }
    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }
    public boolean isBloqueado() {
        return bloqueado;
    }
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    public boolean isEliminado() {
        return eliminado;
    }
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }
    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
    public AdministradorModel getQuienElimino() {
        return quienElimino;
    }
    public void setQuienElimino(AdministradorModel quienElimino) {
        this.quienElimino = quienElimino;
    }
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public AdministradorModel getQuienActualizo() {
        return quienActualizo;
    }
    public void setQuienActualizo(AdministradorModel quienActualizo) {
        this.quienActualizo = quienActualizo;
    }

    //Funciones para tablas many to many
    public void addMision(MisionModel mision) {
        this.misiones.add(mision);
        mision.getUsuarios().add(this);
    }

    public void removeMision(long misionId) {
        MisionModel mision = this.misiones.stream().filter(t -> t.getIdmision() == misionId).findFirst().orElse(null);
        if (mision != null) {
            this.misiones.remove(mision);
            mision.getUsuarios().remove(this);
        }
    }
    public void addLogro(LogroModel logro) {
        this.logros.add(logro);
        logro.getUsuarios().add(this);
    }

    public void removeLogro(long logroId) {
        LogroModel logro = this.logros.stream().filter(t -> t.getIdlogro() == logroId).findFirst().orElse(null);
        if (logro != null) {
            this.logros.remove(logro);
            logro.getUsuarios().remove(this);
        }
    }
    public boolean isSuscrito() {
        return suscrito;
    }

    public void setSuscrito(boolean suscrito) {
        this.suscrito = suscrito;
    }
    //Fin funciones
}
