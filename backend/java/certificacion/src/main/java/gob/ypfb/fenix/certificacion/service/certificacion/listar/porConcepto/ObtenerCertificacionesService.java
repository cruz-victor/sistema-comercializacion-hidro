/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.listar.porConcepto;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObtenerCertificacionesService implements IObtenerCertificacionesService {
    @Autowired
    CertificacionRepository certificacionRepository;

    @Transactional(readOnly = true)
    @Override
    public ObtenerCertificacionesResponse obtenerCertificaciones(ObtenerCertificacionesRequest request){
        List<Certificacion> certificaciones = obtenerTodasCertificacionesPorConcepto(request);
        ObtenerCertificacionesResponse response = establecerRespuesta(certificaciones);
        return response;
    }

    private ObtenerCertificacionesResponse establecerRespuesta(List<Certificacion> certificaciones) {
        ObtenerCertificacionesResponse response= new ObtenerCertificacionesResponse();
        response.setCertificaciones(certificaciones);
        return response;
    }

    private List<Certificacion> obtenerTodasCertificacionesPorConcepto(ObtenerCertificacionesRequest request) {
        List<Certificacion> certificaciones= certificacionRepository.findAll(request.getConcepto());
        return certificaciones;
    }
}
