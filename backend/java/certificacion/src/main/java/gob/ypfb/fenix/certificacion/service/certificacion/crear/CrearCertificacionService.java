/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.crear;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.CertificacionRepository;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CrearCertificacionService implements ICrearCertificacionService {
    @Autowired
    CertificacionRepository certificacionRepository;
    @Autowired
    PartidaRepository partidaRepository;

    @Override
    @Transactional
    public CrearCertificacionResponse crear(CrearCertificacionRequest request) {
        validarExistenciaCertificacion(request);
        Certificacion certificacion = mapearRequestCertificacion(request);
        CrearCertificacionResponse response = establecerRespuesta(certificacion);
        recalcularMontoInicialCertificacion(certificacion);
        return response;
    }

    private void recalcularMontoInicialCertificacion(Certificacion certificacion) {
        if (!certificacion.getConcepto().equals("CTTO")){
            recalcularMontoInicialCertificacionContrato(certificacion);
        }
    }

    private CrearCertificacionResponse establecerRespuesta(Certificacion certificacion) {
        CrearCertificacionResponse response=new CrearCertificacionResponse();
        response.setId(certificacionRepository.save(certificacion).getId());
        return response;
    }

    private Certificacion mapearRequestCertificacion(CrearCertificacionRequest request) {
        Certificacion certificacion=ICertificacionCrearServiceMapper.INSTANCIA.requestToCertificacionPresupuestaria(request);
        return certificacion;
    }

    private void validarExistenciaCertificacion(CrearCertificacionRequest request) {
        if (request.getConcepto().equals("CTTO")){
            Integer totalCertificaciones = certificacionRepository.contarCertificaciones(request.getIdPartida(), request.getConcepto(), request.getFechaEmision(), request.getNumeroCertificacion());
            if (totalCertificaciones > 0) {
                throw new RuntimeException("La certificacion de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " ya existe en la base de datos.");
            }
        }else {
            Integer totalMovimientos = certificacionRepository.contarMovimientos(request.getConcepto(), request.getFechaEmision(), request.getNumeroCertificacion());

            if (totalMovimientos > 0) {
                throw new RuntimeException("La certificacion de fecha " + request.getFechaEmision() + ", concepto " + request.getConcepto() + " y numero de certificacion " + request.getNumeroCertificacion() + " ya existe en la base de datos.");
            }
        }
    }

    private void recalcularMontoInicialCertificacionContrato(Certificacion movimiento)
    {
        //Adenda e Incremento suma al monto inicial de la certificacion
            //Recuperar certificacion de contrato
             Certificacion certificacionRecuperado= certificacionRepository.buscarCertificacionContratoPorId(movimiento.getIdCertificacion());

            //Sumar/Restar el movimiento al monto_maximo de la certificacion de contrato
            //TODO: Recuperar los conceptos de las parametricas (conceptos que incrementan monto_maximo; concepto que descrementan el monto_maximo)
            if (movimiento.getConcepto().equals("ADENDA") || movimiento.getConcepto().equals("INCR")){
                certificacionRecuperado.setMontoMaximo(certificacionRecuperado.getMontoMaximo().add(movimiento.getMontoMaximo()));
            }

            if (movimiento.getConcepto().equals("REV")){
                certificacionRecuperado.setMontoMaximo(certificacionRecuperado.getMontoMaximo().subtract(movimiento.getMontoMaximo()));
            }

            certificacionRecuperado.setFechaRegistro(LocalDate.now());

            //Actualizar la certificacion de contrato
            System.out.println("1----> certificacionRecuperado"+certificacionRecuperado);
            certificacionRecuperado= certificacionRepository.save(certificacionRecuperado);
            System.out.println("2----> certificacionRecuperado"+certificacionRecuperado);

             //Recalcular saldo de la partida
             recalcularSaldoPartida(certificacionRecuperado);

        //Actualizacion mantiene el monto inicial. ACT
            //Mantener el monto_maximo de la cetificacion (???)
    }

    private void recalcularSaldoPartida(Certificacion certificacionContrato){
        //Recuperar partida
        Partida partida=partidaRepository.findPartidaById(certificacionContrato.getIdPartida());

        //Obtener todas las certificaciones de contrato de la partidad recuperada
        List<Certificacion> certificaciones=certificacionRepository.obtenerCertificacionesPorIdPartida(partida.getId());

        //Recalcular el saldo de la partida, monto_actual - sumatoria de monto_maximo de las certificaciones de contrato
        BigDecimal sumatoriaMontosMaximosPartidas=certificaciones.stream()
                .map(certificacion->certificacion.getMontoMaximo())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        partida.setSaldo(partida.getMontoActual().subtract(sumatoriaMontosMaximosPartidas));
        partidaRepository.save(partida);
    }
}
