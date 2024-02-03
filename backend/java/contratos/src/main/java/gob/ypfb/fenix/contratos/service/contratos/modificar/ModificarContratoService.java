/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.modificar;

import gob.ypfb.fenix.contratos.domain.Contrato;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModificarContratoService implements IModificarContratoService {
    @Autowired
    ContratoRepository contratoRepository;

    @Transactional
    public ModificarContratoResponse  modificar(ModificarContratoRequest request) {
        Contrato contrato = buscarContratoPorId(request);
        validarExistenciaContrato(contrato);
        mapearRequestContrato(request, contrato);
        ModificarContratoResponse response = establecerRespuesta(contrato);
        return response;
    }

    private ModificarContratoResponse establecerRespuesta(Contrato contrato) {
        ModificarContratoResponse response=new ModificarContratoResponse();
        response.setId(contratoRepository.save(contrato).getId());
        return response;
    }

    private void mapearRequestContrato(ModificarContratoRequest request, Contrato contrato) {
        contrato.setId(request.getId());
        contrato.setIdTipoContrato(request.getIdTipoContrato());
        contrato.setNumeroContrato(request.getNumeroContrato());
        contrato.setGestionContrato(request.getGestionContrato());
        contrato.setContrato(request.getJsonContrato());
        contrato.setUsuarioAplicacion(request.getUsuarioAplicacion());
        contrato.setJustificacion(request.getJustificacion());
    }

    private Contrato buscarContratoPorId(ModificarContratoRequest request) {
        Contrato contrato=contratoRepository.findById(request.getId(), request.getIdTipoContrato()).orElse(new Contrato());
        return contrato;
    }

    private void validarExistenciaContrato(Contrato contrato) {
        if (contrato.getId()==null)
        {
            log.error("servicio - ModificarContratoService: "+"El objeto contrato es null");
            throw new RuntimeException("servicio - ModificarContratoService: "+"El objeto contrato es null");
        }
    }
}
