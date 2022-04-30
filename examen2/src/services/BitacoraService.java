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


    }


    public String retornarReporte(int clienteId, TipoReporte tipoReporte) {
        String query = "";
        ResultSet resultados;
        String retornar = "No hay resultados";
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
                    query = "SELECT idCliente, accionNuevo.fecha as FechaNuevo, accionNoComprar.fecha as FechaNoComprar, DATEDIFF(accionNoComprar.fecha, accionNuevo.fecha) dias FROM (SELECT fecha, idCliente FROM bitacoras WHERE `status`=\"NUEVO\") accionNuevo JOIN (SELECT fecha, idCliente FROM bitacoras WHERE `status` =\"NO_COMPRAR\") accionNoComprar USING (idCliente) WHERE idCliente="+clienteId;
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        Date fechaNuevo = resultados.getDate("FechaNuevo");
                        Date fechaNoComprar = resultados.getDate("FechaNoComprar");
                        int dias = resultados.getInt("dias");
                        retornar="Fecha de estado nuevo: " + fechaNuevo + ", Fecha de que cancelado: " + fechaNoComprar + ", días: " + dias;
                    }
                    break;
                case TIEMPO_SEGUIMIENTO_Y_POSTERIORES:
                    query = "SELECT idCliente, accionNuevo.fecha as FechaNuevo, accionNoComprar.fecha as FechaNoComprar, DATEDIFF(accionNoComprar.fecha, accionNuevo.fecha) dias FROM (SELECT fecha, idCliente FROM bitacoras WHERE `status`=\"NUEVO\") accionNuevo JOIN (SELECT fecha, idCliente FROM bitacoras WHERE `status` =\"NO_COMPRAR\") accionNoComprar USING (idCliente) WHERE idCliente="+clienteId;
                    resultados = accesoBD.ejecutarSQL(query);
                    while (resultados.next()) {
                        Date fechaNuevo = resultados.getDate("FechaNuevo");
                        Date fechaNoComprar = resultados.getDate("FechaNoComprar");
                        int dias = resultados.getInt("dias");
                        retornar="Fecha de estado nuevo: " + fechaNuevo + ", Fecha de que cancelado: " + fechaNoComprar + ", días: " + dias;
                    }
                    break;
            }
        }catch (SQLException exception){
            retornar="No hay resultados";
        }

        return retornar;
    }



}
