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
                    query = "select v.nombre,v.apellido,v.pago_pendiente,v.compro,TIMESTAMPDIFF(DAY,v.pago_pendiente, IFNULL(v.compro,sysdate())) AS dias_transcurridos\n" +
                            "from (\n" +
                            "select c.nombre,c.apellido,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'PAGO_PENDIENTE') as pago_pendiente,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'COMPRÓ') as compro\n" +
                            "from clientes c \n" +
                            "where c.id in (select b.idCliente from bitacoras b where  status = 'PAGO_PENDIENTE')) as v;";
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        String  nombre = resultados.getString("nombre");
                        String  apellido = resultados.getString("apellido");
                        Date pago_pendiente = resultados.getDate("pago_pendiente");
                        Date compro = resultados.getDate("compro");
                        int dias_transcurridos = resultados.getInt("dias_transcurridos");

                        retornar +=" El cliente: " + nombre +" " + apellido  + " tiene " + dias_transcurridos + " días, pendientes de pago, fecha pago pendiente: "+ pago_pendiente + " fecha compra: " + compro + " \n " ;
                    }
                    break;
                case TIEMPO_SEGUIMIENTO_Y_POSTERIORES:
                    query = "select v.nombre,v.apellido,v.nuevo,v.seguimiento,v.pendiente,v.pago_pendiente,v.compro,\n" +
                            "TIMESTAMPDIFF(DAY, nuevo,seguimiento) AS dias_segumiento,\n" +
                            "TIMESTAMPDIFF(DAY, seguimiento,pendiente) AS dias_pendiente,\n" +
                            "TIMESTAMPDIFF(DAY, seguimiento,pago_pendiente) AS dias_pago_pendiente,\n" +
                            "TIMESTAMPDIFF(DAY, pago_pendiente,compro) AS dias_compra\n" +
                            "from (\n" +
                            "select c.nombre,c.apellido,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'NUEVO') as nuevo,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'SEGUIMIENTO') as seguimiento,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'PENDIENTE') as pendiente,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'PAGO_PENDIENTE') as pago_pendiente,\n" +
                            "(select b.fecha from bitacoras b where b.idCliente = c.id and status = 'COMPRÓ') as compro\n" +
                            "from clientes c \n" +
                            "where c.id in (select b.idCliente from bitacoras b where  status = 'SEGUIMIENTO'))v;";
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        String  nombre = resultados.getString("nombre");
                        String  apellido = resultados.getString("apellido");
                        Date nuevo = resultados.getDate("nuevo");
                        Date seguimiento = resultados.getDate("seguimiento");
                        Date pendiente = resultados.getDate("pendiente");
                        Date pago_pendiente = resultados.getDate("pago_pendiente");
                        Date compro    = resultados.getDate("compro");
                        int dias_segumiento = resultados.getInt("dias_segumiento");
                        int dias_pendiente = resultados.getInt("dias_pendiente");
                        int dias_pago_pendiente = resultados.getInt("dias_pago_pendiente");
                        int dias_compra = resultados.getInt("dias_compra");

                        retornar +=" El cliente: " + nombre +" " + apellido  + " tiene los siguientes  registros de seguimiento: "+
                                 "Creado:" + nuevo + " Seguimiento: " + seguimiento + " pendiente de compra: " + pendiente + " pago pendiente:" + pago_pendiente + " Compra: "+ compro +
                                 " han pasado " + dias_segumiento + " días, hasta el seguimiento, "+ dias_pendiente + " en estado pendiente, " + dias_pago_pendiente + " en estado pendiente de pago,"+
                                 dias_compra +" hasta la compra" +"\n " ;
                    }
                    break;
            }
        }catch (SQLException exception){
            retornar="No hay resultados";
        }

        return retornar;
    }



}
