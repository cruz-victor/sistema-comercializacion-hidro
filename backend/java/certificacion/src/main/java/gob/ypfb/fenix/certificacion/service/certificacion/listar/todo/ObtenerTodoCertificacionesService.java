/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.listar.todo;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObtenerTodoCertificacionesService implements IObtenerTodasCertificacionesService {
    @Autowired
    CertificacionRepository certificacionRepository;

    @Transactional(readOnly = true)
    @Override
    public ObtenerTodasCertificacionesResponse obtenerCertificaciones(){
        List<Certificacion> certificaciones = obtenerTodasCertificaciones();
        ObtenerTodasCertificacionesResponse response = establecerRespuesta(certificaciones);
        return response;
    }

    private ObtenerTodasCertificacionesResponse establecerRespuesta(List<Certificacion> certificaciones) {
        ObtenerTodasCertificacionesResponse response= new ObtenerTodasCertificacionesResponse();
        response.setCertificaciones(certificaciones);
        return response;
    }

    private List<Certificacion> obtenerTodasCertificaciones() {
        List<Certificacion> certificaciones= certificacionRepository.findAll();
        return certificaciones;
    }
}
