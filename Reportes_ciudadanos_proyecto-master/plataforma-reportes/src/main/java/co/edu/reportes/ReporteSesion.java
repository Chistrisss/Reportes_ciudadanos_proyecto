package co.edu.reportes;

import java.io.Serializable;

public class ReporteSesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String barrio;
    private String nivelUrgencia;
    private Integer diasSinAtencion;

    public ReporteSesion() {
    }

    public ReporteSesion(String titulo, String barrio, String nivelUrgencia, Integer diasSinAtencion) {
        this.titulo = titulo;
        this.barrio = barrio;
        this.nivelUrgencia = nivelUrgencia;
        this.diasSinAtencion = diasSinAtencion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getNivelUrgencia() {
        return nivelUrgencia;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public Integer getDiasSinAtencion() {
        return diasSinAtencion;
    }

    public void setDiasSinAtencion(Integer diasSinAtencion) {
        this.diasSinAtencion = diasSinAtencion;
    }
}