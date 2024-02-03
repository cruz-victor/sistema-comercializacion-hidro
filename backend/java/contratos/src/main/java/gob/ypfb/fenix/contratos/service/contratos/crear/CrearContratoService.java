/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.crear;

import gob.ypfb.fenix.contratos.domain.Contrato;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CrearContratoService implements ICrearContratoService {

    @Autowired
    ContratoRepository contratoRepository;

    @Override
    @Transactional
    public CrearContratoResponse crear(CrearContratoRequest request) {
        Contrato contrato = new Contrato();
        mapearRequestContrato(request, contrato);
        contrato = contratoRepository.save(contrato);
        return new CrearContratoResponse(contrato.getId());
    }

    private void mapearRequestContrato(CrearContratoRequest request, Contrato contrato) {
        contrato.setIdTipoContrato(request.getIdTipoContrato());
        contrato.setNumeroContrato(request.getNumeroContrato());
        contrato.setGestionContrato(request.getGestionContrato());
        contrato.setContrato(request.getJsonContrato());
        contrato.setFechaRegistro(LocalDate.now());
        contrato.setEliminado(false);
        contrato.setUsuarioAplicacion(request.getUsuarioAplicacion());
        contrato.setJustificacion(null);
        contrato.setIpCliente(request.getIpCliente());
        contrato.setHash(null);
    }
}
