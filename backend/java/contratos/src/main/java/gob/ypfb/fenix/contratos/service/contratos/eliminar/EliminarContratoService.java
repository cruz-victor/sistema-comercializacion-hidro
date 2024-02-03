/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.eliminar;

import gob.ypfb.fenix.contratos.domain.Contrato;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarContratoService implements IEliminarContratoService {
    @Autowired
    ContratoRepository contratoRepository;

    @Transactional
    public EliminarContratoResponse eliminarLogicamente(EliminarContratoRequest request) {
        Contrato contrato = buscarContratoPorId(request);
        mapearRequestContrato(request, contrato);
        validarExistenciaContrato(contrato);
        EliminarContratoResponse response = establecerRespuesta(contrato);
        return response;
    }

    private EliminarContratoResponse establecerRespuesta(Contrato contrato) {
        EliminarContratoResponse response=new EliminarContratoResponse();
        response.setId(contratoRepository.save(contrato).getId());
        return response;
    }

    private void mapearRequestContrato(EliminarContratoRequest request, Contrato contrato) {
        contrato.setJustificacion(request.getJustificacion());
        contrato.setUsuarioAplicacion(request.getUsuario());
        contrato.setEliminado(request.getEliminado());
    }

    private Contrato buscarContratoPorId(EliminarContratoRequest request) {
        Contrato contrato=contratoRepository.findById(request.getId(), request.getTipoContrato()).orElse(new Contrato());
        return contrato;
    }

    private void validarExistenciaContrato(Contrato contrato) {
        if (contrato.getId()==null){
            log.error("servicio - EliminarContratoService: "+"El objeto contrato es null");
            throw new RuntimeException("servicio - EliminarContratoService: "+"El objeto contrato es null");
        }
    }
}
