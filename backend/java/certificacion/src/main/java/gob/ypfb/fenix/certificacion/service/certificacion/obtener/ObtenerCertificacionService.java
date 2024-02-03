/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.obtener;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObtenerCertificacionService implements IObtenerCertificacionService {
    @Autowired
    CertificacionRepository certificacionRepository;

    @Transactional(readOnly = true)
    public ObtenerCertificacionResponse obtenerCertificacionPorId(ObtenerCertificacionRequest request) {
        Certificacion certificacion = buscarCertificacionPorId(request);
        ObtenerCertificacionResponse response = establecerRespuesta(certificacion);
        return response;
    }

    private ObtenerCertificacionResponse establecerRespuesta(Certificacion certificacion) {
        ObtenerCertificacionResponse response= new ObtenerCertificacionResponse();
        response.setCertificacion(certificacion);
        return response;
    }

    private Certificacion buscarCertificacionPorId(ObtenerCertificacionRequest request) {
        Certificacion certificacion= certificacionRepository.findById(request.getId()).orElse(new Certificacion());
        return certificacion;
    }
}
