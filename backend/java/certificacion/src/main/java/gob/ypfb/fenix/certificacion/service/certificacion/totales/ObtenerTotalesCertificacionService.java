/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.totales;

import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtenerTotalesCertificacionService implements IObtenerTotalesCertificacionService {

    @Autowired
    CertificacionRepository certificacionPresupuestariaRepository;

    public ObtenerTotalesCertificacionResponse obtenerTotales(ObtenerTotalesCertificacionRequest request) {
        String totalesDashboard = obtenerJsonTotalesCertificacion(request);
        ObtenerTotalesCertificacionResponse response = establecerRespuesta(totalesDashboard);
        return response;
    }

    private ObtenerTotalesCertificacionResponse establecerRespuesta(String totalesDashboard) {
        ObtenerTotalesCertificacionResponse response=new ObtenerTotalesCertificacionResponse();
        response.setTotalesDashboard(totalesDashboard);
        return response;
    }

    private String obtenerJsonTotalesCertificacion(ObtenerTotalesCertificacionRequest request) {
        String totalesDashboard= certificacionPresupuestariaRepository.obtenerTotalesDashboard(request.getGestion());
        return totalesDashboard;
    }
}
