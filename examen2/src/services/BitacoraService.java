package services;

import accesodatos.AccesoBD;
import entities.Bitacora;
import entities.Cliente;
import entities.TipoReporte;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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

        String query2 = "UPDATE clientes SET status = '" + bitacora.getStatus()+ "'  WHERE id = "+bitacora.getCliente().getId()+" ;";
        accesoBD.ejecutarQuery(query2);
    }


    public String retornarReporte(int clienteId, TipoReporte tipoReporte) {
        String query = "";
        ResultSet resultados;
        String retornar = "";
        try{
            switch (tipoReporte){
                case DIAS_ENTRE_NUEVO_Y_COMPRA:
                    query = "SELECT idCliente, accionNuevo.fecha as FechaNuevo, accionComprar.fecha FechaComprar, DATEDIFF(accionComprar.fecha, accionNuevo.fecha) dias FROM (SELECT fecha, idCliente FROM bitacoras WHERE `status`=\"NUEVO\") accionNuevo JOIN (SELECT fecha, idCliente FROM bitacoras WHERE `status` =\"COMPRÓ\") accionComprar USING (idCliente) WHERE idCliente="+clienteId;
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        Date fechaNuevo = resultados.getDate("FechaNuevo");
                        Date fechaComprar = resultados.getDate("FechaComprar");
                        int dias = resultados.getInt("dias");
                        retornar="Fecha de estado nuevo: " + fechaNuevo + ", Fecha de que compró: " + fechaComprar + ", días: " + dias;
                    }
                    break;
                case DIAS_PROMEDIO_CANCELANDO:
                    query = "SELECT idCliente, accionNuevo.fecha as FechaNuevo, accionNoComprar.fecha as FechaNoComprar, DATEDIFF(accionNoComprar.fecha, accionNuevo.fecha) dias FROM (SELECT fecha, idCliente FROM bitacoras WHERE `status`=\"NUEVO\") accionNuevo JOIN (SELECT fecha, idCliente FROM bitacoras WHERE `status` =\"NO_COMPRAR\") accionNoComprar USING (idCliente) WHERE idCliente="+clienteId;
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        Date fechaNuevo = resultados.getDate("FechaNuevo");
                        Date fechaNoComprar = resultados.getDate("FechaNoComprar");
                        int dias = resultados.getInt("dias");
                        retornar="Fecha de estado nuevo: " + fechaNuevo + ", Fecha de que cancelado: " + fechaNoComprar + ", días: " + dias;
                    }

                    break;

                case PENDIENTES_Y_DIAS:
                    query = "select c.nombre,c.apellido,\n" +
                            " TIMESTAMPDIFF(DAY, c.creado, (select b.fecha from bitacoras b where b.idCliente = c.id and status = 'PAGO_PENDIENTE')) AS dias_transcurridos\n" +
                            "from clientes c \n" +
                            "where c.status = 'PAGO_PENDIENTE' \n";
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        String  nombre = resultados.getString("nombre");
                        String  apellido = resultados.getString("apellido");
                        int dias_transcurridos = resultados.getInt("dias_transcurridos");

                        retornar +=" El cliente: " + nombre +" " + apellido  + " tiene " + dias_transcurridos + " pendientes de pago \n " ;
                    }
                    break;
                case TIEMPO_SEGUIMIENTO_Y_POSTERIORES:
                    query = "select c.nombre,c.apellido,\n" +
                            " TIMESTAMPDIFF(DAY, c.creado,(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'SEGUIMIENTO')) AS dias_transcurridos\n" +
                            "from clientes c ";
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        String  nombre = resultados.getString("nombre");
                        String  apellido = resultados.getString("apellido");
                        int dias_transcurridos = resultados.getInt("dias_transcurridos");

                        retornar +=" El cliente: " + nombre +" " + apellido  + " tiene " + dias_transcurridos + " hasta el segumiento \n " ;
                    }
                    break;
            }
        }catch (SQLException exception){
            retornar="No hay resultados";
        }

        return retornar;
    }



}
