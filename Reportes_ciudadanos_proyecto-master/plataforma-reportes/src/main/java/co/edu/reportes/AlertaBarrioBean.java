package co.edu.reportes;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("alertaBarrioBean")
@ViewScoped
public class AlertaBarrioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreCiudadano;
    private String localidad;
    private String correo;
    private String confirmacion;

    public void suscribirAlerta() {
        confirmacion = "Hola " + nombreCiudadano.trim()
                + ", tu suscripción está activa para la localidad de " + localidad
                + ". Enviaremos las alertas de nuevos reportes al correo "
                + correo.trim() + ".";
    }

    public String getNombreCiudadano() {
        return nombreCiudadano;
    }

    public void setNombreCiudadano(String nombreCiudadano) {
        this.nombreCiudadano = nombreCiudadano;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }
}