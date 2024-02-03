/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.eliminar;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarCertificacionService implements IEliminarCertificacionService {
    @Autowired
    CertificacionRepository certificacionRepository;

    @Transactional
    public EliminarCertificacionResponse eliminarLogicamente(EliminarCertificacionRequest request) {
        Certificacion certificacionPresupuestaria = buscarCertificacionPorId(request);
        mapearRequestCertificacion(request, certificacionPresupuestaria);
        validarExistenciaCertificacion(certificacionPresupuestaria);
        EliminarCertificacionResponse response = establecerRespuesta(certificacionPresupuestaria);

        return response;
    }

    private EliminarCertificacionResponse establecerRespuesta(Certificacion certificacionPresupuestaria) {
        EliminarCertificacionResponse response=new EliminarCertificacionResponse();
        response.setId(certificacionRepository.save(certificacionPresupuestaria).getId());
        return response;
    }

    private void mapearRequestCertificacion(EliminarCertificacionRequest request, Certificacion certificacionPresupuestaria) {
        certificacionPresupuestaria.setJustificacion(request.getJustificacion());
        certificacionPresupuestaria.setUsuarioAplicacion(request.getUsuario());
        certificacionPresupuestaria.setEliminado(request.getEliminado());
    }

    private Certificacion buscarCertificacionPorId(EliminarCertificacionRequest request) {
        Certificacion certificacionPresupuestaria= certificacionRepository.findById(request.getId()).orElse(new Certificacion());
        return certificacionPresupuestaria;
    }

    private void validarExistenciaCertificacion(Certificacion cisterna) {
        if (cisterna.getId()==null){
            log.error("servicio - EliminarCertificacionService: "+"El objeto certificacion es null");
            throw new RuntimeException("servicio - EliminarCertificacionService: "+"El objeto certificacion es null");
        }
    }
}
