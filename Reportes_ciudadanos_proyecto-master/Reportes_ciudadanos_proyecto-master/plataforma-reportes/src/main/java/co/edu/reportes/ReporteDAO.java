package co.edu.reportes;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    private static final String SELECT_BASE =
        "SELECT r.id, r.titulo, r.barrio, r.descripcion, r.latitud, r.longitud, r.estado, r.fecha, u.nombre_usuario " +
        "FROM reportes r JOIN usuarios u ON u.id = r.usuario_id ";


    public void guardar(Reporte r, int usuarioId) throws SQLException {
        String sql = "INSERT INTO reportes (usuario_id, categoria_id, titulo, barrio, descripcion, latitud, longitud) " +
                     "VALUES (?, (SELECT categoria_id FROM tipos_problema WHERE nombre = ?), ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, r.getTitulo());
            ps.setString(3, r.getTitulo());
            ps.setString(4, r.getBarrio());
            ps.setString(5, r.getDescripcion());
            ps.setObject(6, r.getLatitud());
            ps.setObject(7, r.getLongitud());
            ps.executeUpdate();
        }
    }


    public List<Reporte> listar() throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY r.fecha DESC, r.id DESC";

        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }


    public List<Reporte> listarPorBarrio(String barrio) throws SQLException {
        List<Reporte> lista = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE r.barrio LIKE ? ORDER BY r.fecha DESC, r.id DESC";

        try (Connection cn = Conexion.obtener();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + barrio + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }


    private Reporte mapear(ResultSet rs) throws SQLException {
        BigDecimal lat = rs.getBigDecimal("latitud");
        BigDecimal lng = rs.getBigDecimal("longitud");

        Reporte r = new Reporte(
            rs.getString("titulo"),
            rs.getString("barrio"),
            rs.getString("descripcion"),
            lat == null ? null : lat.doubleValue(),
            lng == null ? null : lng.doubleValue()
        );
        r.setId(rs.getInt("id"));
        r.setUsuario(rs.getString("nombre_usuario"));
        r.setEstado(rs.getString("estado"));
        r.setFecha(rs.getTimestamp("fecha"));
        return r;
    }
}
