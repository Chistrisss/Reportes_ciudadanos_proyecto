package co.edu.reportes;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String barrio;
    private String descripcion;
    private Double latitud;
    private Double longitud;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "categoria_id")
    private Integer categoriaId;

    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public Reporte() {}


    public Reporte(String titulo, String barrio, String descripcion, Double latitud, Double longitud) {
        this.titulo = titulo;
        this.barrio = barrio;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = "Pendiente";
        this.fecha = new Date();
    }


    public String resumen() {
        return (titulo != null ? titulo : "Sin título") + " - " +
                (barrio != null ? barrio : "Sin localidad") + " (" +
                (descripcion != null ? descripcion : "") + ")";
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}