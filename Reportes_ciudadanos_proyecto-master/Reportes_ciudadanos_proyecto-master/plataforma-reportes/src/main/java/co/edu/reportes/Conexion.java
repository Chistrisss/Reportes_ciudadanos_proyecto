package co.edu.reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://localhost:3306/reportes_ciudadanos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Bogota";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection obtener() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Revisa la dependencia en el pom.xml", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}