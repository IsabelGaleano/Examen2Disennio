package entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Producto {
    private int id;
    private AtomicInteger idGenerator = new AtomicInteger(1000);
    private String nombre;
    private int cantidad;
    private double precio;
    private double importe;


    public Producto() {
    }

    public Producto(String nombre, int cantidad, double precio, double importe) {
        this.id = idGenerator.getAndIncrement();
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AtomicInteger getIdGenerator() {
        return idGenerator;
    }

    public void setIdGenerator(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", importe=" + importe +
                '}';
    }
}
