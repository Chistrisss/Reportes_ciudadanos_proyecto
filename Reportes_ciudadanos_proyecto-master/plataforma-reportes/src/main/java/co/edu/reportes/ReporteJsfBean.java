package co.edu.reportes;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("reporteJsfBean")
@SessionScoped
public class ReporteJsfBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String barrio;
    private String nivelUrgencia;
    private Integer diasSinAtencion;
    private final List<ReporteSesion> listaReportes = new ArrayList<>();

    public String agregarReporte() {
        listaReportes.add(new ReporteSesion(
                titulo,
                barrio,
                nivelUrgencia,
                diasSinAtencion
        ));
        titulo = null;
        barrio = null;
        nivelUrgencia = null;
        diasSinAtencion = null;
        return null;
    }

    public int getTotalReportes() {
        return listaReportes.size();
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

    public List<ReporteSesion> getListaReportes() {
        return listaReportes;
    }
}