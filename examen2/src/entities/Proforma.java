package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Proforma {
    private int idFactura;
    private AtomicInteger idGenerator = new AtomicInteger(1000);
    private Cliente cliente;
    private Date fecha;
    private Date fechaVencimiento;
    private ArrayList<Producto> productos;

    public Proforma() {
    }

    public Proforma(Cliente cliente, Date fecha, Date fechaVencimiento, ArrayList<Producto> productos) {
        this.idFactura = idGenerator.getAndIncrement();
        this.cliente = cliente;
        this.fecha = fecha;
        this.fechaVencimiento = fechaVencimiento;
        this.productos = productos;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Protoforma{" +
                "idFactura=" + idFactura +
                ", cliente=" + cliente +
                ", fecha=" + fecha +
                ", fechaVencimiento=" + fechaVencimiento +
                ", productos=" + productos +
                '}';
    }
}
