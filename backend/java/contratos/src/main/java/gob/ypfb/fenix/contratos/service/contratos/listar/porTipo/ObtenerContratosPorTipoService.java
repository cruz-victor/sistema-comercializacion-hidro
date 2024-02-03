/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.listar.porTipo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerContratosPorTipoService implements IObtenerContratosPorTipoService {

    @Autowired
    ContratoRepository contratoRepository;

    @Transactional(readOnly = true)
    public ObtenerContratosPorTipoResponse obtenerContratos(ObtenerContratosPorTipoRequest request) {
        List<ContratoResponse> contratos = obtenerContratosPorTipoContrato(request);
        ObtenerContratosPorTipoResponse response = establecerRespuesta(contratos);
        return response;

    }

    private ObtenerContratosPorTipoResponse establecerRespuesta(List<ContratoResponse> contratos) {
        ObtenerContratosPorTipoResponse response = new ObtenerContratosPorTipoResponse();
        response.setContratos(contratos);
        return response;
    }

    private List<ContratoResponse> obtenerContratosPorTipoContrato(ObtenerContratosPorTipoRequest request) {
        List<ContratoResponse> contratos=contratoRepository.findAllContratosPorTipo(request.getTipoContrato()).stream()
                .map(contrato -> {
                    ContratoResponse c=new ContratoResponse();
                    c.setId(contrato.getId());
                    c.setContrato( getContratoJson(contrato.getContrato()));
                    return c;
                })
                .collect(Collectors.toList());
        return contratos;
    }

    @Transactional(readOnly = true)
    public ObtenerContratosPorTipoResponse obtenerContratosGestion(ObtenerContratosPorTipoRequest request) {
        List<ContratoResponse> contratos = obtenerContratosPorTipoGestion(request);
        ObtenerContratosPorTipoResponse response = establecerRespuesta(contratos);
        return response;
    }

    private List<ContratoResponse> obtenerContratosPorTipoGestion(ObtenerContratosPorTipoRequest request) {
        List<ContratoResponse> contratos=contratoRepository.findAllContratosPorTipoGestion(request.getTipoContrato(), request.getGestionContrato())
                .stream()
                .map(contrato -> {
                    ContratoResponse c=new ContratoResponse();
                    c.setId(contrato.getId());
                    c.setContrato( getContratoJson(contrato.getContrato()));
                    return c;
                })
                .collect(Collectors.toList());
        return contratos;
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
