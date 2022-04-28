package entities;

import java.util.Date;

public class Cliente {
    private int Id;
    private String nombre;
    private String apellido;
    private String status;
    private String telefono;
    private Date followup;
    private Date creado;
    private Date modificado;


    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String status, String telefono, Date followup, Date creado, Date modificado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.status = status;
        this.telefono = telefono;
        this.followup = followup;
        this.creado = creado;
        this.modificado = modificado;
    }

    public Cliente(int id, String nombre, String apellido, String status, String telefono, Date followup, Date creado, Date modificado) {
        Id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.status = status;
        this.telefono = telefono;
        this.followup = followup;
        this.creado = creado;
        this.modificado = modificado;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFollowup() {
        return followup;
    }

    public void setFollowup(Date followup) {
        this.followup = followup;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", status='" + status + '\'' +
                ", telefono='" + telefono + '\'' +
                ", followup=" + followup +
                ", creado=" + creado +
                ", modificado=" + modificado +
                '}';
    }
}
