package co.edu.reportes;

import java.sql.Timestamp;

public class Reporte {
    private int id;
    private String titulo;
    private String barrio;
    private String descripcion;
    private Double latitud;    // Double (y no double) porque puede ser null
    private Double longitud;
    private String usuario;
    private String estado;
    private Timestamp fecha;


    public Reporte(String titulo, String barrio, String usuario) {
        this.titulo = titulo;
        this.barrio = barrio;
        this.usuario = usuario;
    }


    public Reporte(String titulo, String barrio, String descripcion, Double latitud, Double longitud) {
        this.titulo = titulo;
        this.barrio = barrio;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String resumen() {
        return titulo + " - Barrio " + barrio + " (" + descripcion + ")";
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getBarrio() { return barrio; }
    public String getDescripcion() { return descripcion; }
    public Double getLatitud() { return latitud; }
    public Double getLongitud() { return longitud; }
    public String getUsuario() { return usuario; }
    public String getEstado() { return estado; }
    public Timestamp getFecha() { return fecha; }

    public void setId(int id) { this.id = id; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
}
