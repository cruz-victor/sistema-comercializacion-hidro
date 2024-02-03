/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.obtener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gob.ypfb.fenix.contratos.domain.Contrato;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObtenerContratoService implements IObtenerContratoService {
    @Autowired
    ContratoRepository contratoRepository;

    @Transactional(readOnly = true)
    public ObtenerContratoResponse obtenerContratoPorId(ObtenerContratoRequest request) {
        Contrato contrato = buscarContratoPorIdTipo(request);
        ContratoResponse contratoResponse=new ContratoResponse(contrato.getId(),getContratoJson(contrato.getContrato()));
        ObtenerContratoResponse response = establecerRespuesta(contratoResponse);
        return response;
    }

    private ObtenerContratoResponse establecerRespuesta(ContratoResponse contratoResponse) {
        ObtenerContratoResponse response = new ObtenerContratoResponse();
        response.setContrato(contratoResponse);
        return response;
    }

    private Contrato buscarContratoPorIdTipo(ObtenerContratoRequest request) {
        Contrato contrato=contratoRepository.findById(request.getId(), request.getTipoContrato()).orElse(new Contrato());
        return contrato;
    }

    @Transactional(readOnly = true)
    public ObtenerContratoResponse obtenerContratoPorCodigo(ObtenerContratoRequest request) {
        Contrato contrato = buscarContratoPorCodigo(request);
        ContratoResponse contratoResponse=new ContratoResponse(contrato.getId(),getContratoJson(contrato.getContrato()));
        ObtenerContratoResponse response = establecerRespuesta(contratoResponse);
        return response;
    }

    private Contrato buscarContratoPorCodigo(ObtenerContratoRequest request) {
        Contrato contrato=contratoRepository.findByCodigo(request.getTipoContrato(), request.getNumeroContrato(), request.getGestionContrato()).orElse(new Contrato());
        return contrato;
    }

    private JsonNode getContratoJson(String contrato) {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(contrato);
            return node;
        }
        catch(Exception e){
            return null;
        }
    }
}
