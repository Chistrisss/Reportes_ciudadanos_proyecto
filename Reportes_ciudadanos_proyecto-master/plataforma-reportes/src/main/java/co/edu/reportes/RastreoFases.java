package co.edu.reportes;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import java.util.logging.Logger;

public class RastreoFases implements PhaseListener {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RastreoFases.class.getName());

    @Override
    public void beforePhase(PhaseEvent event) {
        LOG.info("[JSF - CICLO DE VIDA] INICIO FASE: " + event.getPhaseId());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        LOG.info("[JSF - CICLO DE VIDA] FIN FASE:    " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}