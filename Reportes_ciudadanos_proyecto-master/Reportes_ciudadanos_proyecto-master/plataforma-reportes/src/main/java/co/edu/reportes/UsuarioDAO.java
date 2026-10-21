package co.edu.reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {


    public Integer validar(String usuario, String clave) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE nombre_usuario = ? AND clave_hash = SHA2(?, 256)";

        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return null;
    }
}
