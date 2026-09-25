package co.edu.reportes;

public class Reporte {
    private String titulo;
    private String barrio;
    private String usuario;

    // Constructor
    public Reporte(String titulo, String barrio, String usuario) {
        this.titulo = titulo;
        this.barrio = barrio;
        this.usuario = usuario;
    }

    // Getters para poder leer la información después
    public String getTitulo() { return titulo; }
    public String getBarrio() { return barrio; }
    public String getUsuario() { return usuario; }
}