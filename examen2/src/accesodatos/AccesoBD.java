package accesodatos;

import java.sql.*;

public class AccesoBD {

    private String driver;
    private Statement stmt;
    private Connection conn;

    public AccesoBD() throws Exception {
        try {
            driver = "com.mysql.cj.jdbc.Driver";

            Class.forName(driver);
            String connectionUrl1 = "jdbc:mysql://localhost/bddisennio?user=root&password=Hola123&serverTimezone=UTC";
            conn = DriverManager.getConnection(connectionUrl1);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void ejecutarQuery(String query) throws SQLException {
        try {
            stmt.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet ejecutarSQL(String query) throws SQLException{
        ResultSet rs = null;
        try{
            rs =stmt.executeQuery(query);
            return rs;
        }
        catch (SQLException e){
            throw  e;
        }
    }
}
