package services;

import accesodatos.AccesoBD;
import entities.Bitacora;
import entities.Cliente;

import java.text.SimpleDateFormat;

public class BitacoraService {
    static AccesoBD accesoBD;

    public BitacoraService() throws Exception {
        accesoBD= new AccesoBD();
    }

    public void crearBitacora(Bitacora bitacora) throws Exception {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fecha= simpleDateFormat.format(bitacora.getFecha());

        String query = "INSERT INTO Bitacoras (idCliente, status, fecha)" +
                "VALUES (" + bitacora.getCliente().getId() + ", '" + bitacora.getStatus() + "', '" + fecha + "');";

        accesoBD.ejecutarQuery(query);


    }
}
