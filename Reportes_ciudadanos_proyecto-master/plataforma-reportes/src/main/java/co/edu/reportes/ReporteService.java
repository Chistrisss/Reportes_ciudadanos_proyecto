package co.edu.reportes;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReporteService {

    @PersistenceContext(unitName = "reportes-unit")
    private EntityManager em;

    public void guardar(Reporte reporte, String nombreCategoria) {

        reporte.setCategoriaId(1);

        em.persist(reporte);
        em.flush();
    }

    public List<Reporte> listar() {

        return em.createQuery("SELECT r FROM Reporte r ORDER BY r.fecha DESC, r.id DESC", Reporte.class)
                .setHint("eclipselink.refresh", "true")
                .getResultList();
    }

    public List<Reporte> listarPorBarrio(String barrio) {
        return em.createQuery("SELECT r FROM Reporte r WHERE r.barrio LIKE :barrio ORDER BY r.fecha DESC, r.id DESC", Reporte.class)
                .setParameter("barrio", "%" + barrio + "%")
                .setHint("eclipselink.refresh", "true")
                .getResultList();
    }
}