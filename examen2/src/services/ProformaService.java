package services;

import entities.Cliente;
import entities.Producto;
import entities.Proforma;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProformaService {

    public ProformaService() {
    }

    public String enviarProforma(Cliente cliente) throws Exception {

        ArrayList<Producto> productos = new ArrayList<>();

        productos.add(new Producto("Computadora Asus",1,4500000,5750));
        productos.add(new Producto("Computadora HP",1,7500000,5650));
        productos.add(new Producto("Computadora Lenovo",1,6500000,5450));
        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse("12/03/2022");
        Date fechaVencimiento = new SimpleDateFormat("dd/MM/yyyy").parse("12/03/2022");
        Proforma proforma = new Proforma(cliente,fecha, fechaVencimiento,productos);
        return proforma.toString();
    }
}
