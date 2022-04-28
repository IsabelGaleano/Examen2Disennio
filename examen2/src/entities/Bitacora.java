package entities;

import java.util.Date;

public class Bitacora {
    private int Id;
    private Cliente cliente;
    private String status;
    private Date fecha;

    public Bitacora() {
    }

    public Bitacora(Cliente cliente, String status, Date fecha) {
        this.cliente = cliente;
        this.status = status;
        this.fecha = fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "Id=" + Id +
                ", cliente=" + cliente +
                ", status='" + status + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
