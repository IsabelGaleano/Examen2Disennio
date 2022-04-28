package services;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import accesodatos.AccesoBD;
import entities.Bitacora;
import entities.Cliente;
import org.omg.CORBA.PRIVATE_MEMBER;

public class ClienteService {
    static AccesoBD accesoBD;

    static BitacoraService bitacoraService;
    public ClienteService() throws Exception {
         accesoBD= new AccesoBD();
         bitacoraService = new BitacoraService();
    }

    public static Cliente crearCliente(Cliente cliente) throws Exception {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        //Se formatean las fechas
        String followup= simpleDateFormat.format(cliente.getFollowup());
        String creado = simpleDateFormat.format(cliente.getCreado());
        String modificado = simpleDateFormat.format(cliente.getModificado());

        String query = "INSERT INTO Clientes (nombre, apellido, status, telefono,followup,creado,modificado)\n" +
                "VALUES ('" + cliente.getNombre() + "', '" + cliente.getApellido() + "', '" + cliente.getStatus() + "', " +
                "'" + cliente.getTelefono() + "', '" + followup + "', '" + creado + "', '" + modificado + "');";

        //Se inserta el cliente
        accesoBD.ejecutarQuery(query);

        //Funcion que retorna el cliente insertado
        Cliente tmpCliente = clienteCreado();

        //Aqui se crea la primera bitacora con el cliente nuevo
        Date fechaBitacora = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");

        bitacoraService.crearBitacora(new Bitacora(tmpCliente,"NUEVO", fechaBitacora));

        return tmpCliente;
    }

    private static Cliente clienteCreado() throws Exception {
        Cliente cliente = new Cliente();
        //Busca el ultimo cliente creado
        String query = "SELECT * FROM Clientes ORDER BY ID DESC LIMIT 1";

        ResultSet rs = accesoBD.ejecutarSQL(query);

        while (rs.next()) {
            cliente = new Cliente(rs.getInt("ID"), rs.getString("nombre"),rs.getString("apellido"),
                    rs.getString("status"),rs.getString("telefono"),rs.getDate("followup"),rs.getDate("creado"),
                    rs.getDate("modificado"));

        }

        return cliente;
    }
}
