package main;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //INSERT, UPDATE, DELETE
        Class.forName("com.mysql.cj.jdbc.Driver");
//        String query1 = "INSERT into avanceUsuarioFReto (idUsuarioF, idReto, nombreHito) VALUES (" + tmpAvance.getIdUsuario() + ", " + tmpAvance.getIdReto() + ",'" + tmpAvance.getNombreHito() + "')";
        Statement stmt1 = null;
        Connection conn1 = null;
        String connectionUrl1 = "jdbc:mysql://localhost/proyectoFinal?user=root&password=p@ssw0rd1";
        conn1 = DriverManager.getConnection(connectionUrl1);
        stmt1 = conn1.createStatement();
//        stmt1.execute(query1);

        //SELECT
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn2 = null;
        Statement stmt2 = null;
        ResultSet rs2 = null;
        String connectionUrl2 = "jdbc:mysql://localhost/proyectoFinal?user=root&password=p@ssw0rd1&useSSL=false";
        String query2 = "SELECT idUsuarioF, idReto, nombreHito FROM avanceUsuarioFReto WHERE idUsuarioF = " + idUsuario;
        conn2 = DriverManager.getConnection(connectionUrl2);
        stmt2 = conn2.createStatement();
        rs2 = stmt2.executeQuery(query2);
        while(rs2.next())
        {
//            System.out.println(rs2.);
//            Avance tmpAvance = new Avance(rs.getInt("idUsuarioF"), rs.getInt("idReto"), rs.getString("nombreHito"));
//            avances.add(tmpAvance);
        }
    }
}
