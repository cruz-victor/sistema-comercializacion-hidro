/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.modificar;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModificarCertificacionService implements IModificarCertificacionService {
    @Autowired
    CertificacionRepository certificacionRepository;

    @Transactional
    public ModificarCertificacionResponse modificar(ModificarCertificacionRequest request) {
        Certificacion certificacion = buscarCertificacionPorId(request);
        validarExistenciaIdCertificacion(certificacion);
        certificacion = mapearRequestCertificacion(request);
        ModificarCertificacionResponse response = establecerRespuesta(certificacion);
        validarExistenciaCertificacionDuplicada(request);

        return response;
    }

    private ModificarCertificacionResponse establecerRespuesta(Certificacion certificacion) {
        ModificarCertificacionResponse response=new ModificarCertificacionResponse();
        response.setId(certificacionRepository.save(certificacion).getId());
        return response;
    }

    private Certificacion mapearRequestCertificacion(ModificarCertificacionRequest request) {
        Certificacion certificacion;
        certificacion=ICertificacionModificarServiceMapper.INSTANCIA.requestToCertificacionPresupuestaria(request);
        return certificacion;
    }

    private Certificacion buscarCertificacionPorId(ModificarCertificacionRequest request) {
        Certificacion certificacion = certificacionRepository.findById(request.getId()).orElse(new Certificacion());
        return certificacion;
    }

    private void validarExistenciaIdCertificacion(Certificacion certificacion) {
        if (certificacion.getId()==null)
        {
            log.error("servicio - ModificarCertificacionResponse: "+"El objeto certificacion es null");
            throw new RuntimeException("servicio - ModificarCertificacionResponse: "+"El objeto certificacion es null");
        }
    }

    private void validarExistenciaCertificacionDuplicada(ModificarCertificacionRequest request) {
        if (request.getConcepto().equals("CTTO")){
            Integer totalCertificaciones = certificacionRepository.contarCertificaciones(request.getIdPartida(), request.getConcepto(), request.getFechaEmision(), request.getNumeroCertificacion());
            if (totalCertificaciones == 0) {
                throw new RuntimeException("No existe la certificacion de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " en la base de datos.");
            }
            if (totalCertificaciones > 1) {
                throw new RuntimeException("Existen varias certificaciones de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " en la base de datos.");
            }
        }else {
            Integer totalMovimientos = certificacionRepository.contarMovimientos(request.getConcepto(), request.getFechaEmision(), request.getNumeroCertificacion());

            if (totalMovimientos == 0) {
                throw new RuntimeException("No existe la certificacion de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " en la base de datos.");
            }
            if (totalMovimientos > 1) {
                throw new RuntimeException("Existen varias certificaciones de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " en la base de datos.");
            }
        }
    }
}
