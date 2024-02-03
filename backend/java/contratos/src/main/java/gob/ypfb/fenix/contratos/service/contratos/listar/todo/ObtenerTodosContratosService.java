/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.listar.todo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerTodosContratosService implements IObtenerTodosContratosService {

    @Autowired
    ContratoRepository contratoRepository;

    @Transactional(readOnly = true)
    public ObtenerTodosContratosResponse obtenerContratos() {
        List<ContratoResponse> contratos = obtenerTodosContratos();
        ObtenerTodosContratosResponse response = establecerRespuesta(contratos);
        return response;
    }

    private ObtenerTodosContratosResponse establecerRespuesta(List<ContratoResponse> contratos) {
        ObtenerTodosContratosResponse response = new ObtenerTodosContratosResponse();
        response.setContratos(contratos);
        return response;
    }

    private List<ContratoResponse> obtenerTodosContratos() {
        return contratoRepository.findAllContratos().stream()
                    .map(contrato -> {
                        ContratoResponse c=new ContratoResponse();
                        c.setId(contrato.getId());
                        c.setContrato( getContratoJson(contrato.getContrato()));
                        return c;
                    })
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ObtenerTodosContratosResponse obtenerContratosGestion(ObtenerTodosContratosRequest request) {
        List<ContratoResponse> contratos = obtenerTodosContratosPorGestion(request);
        ObtenerTodosContratosResponse response = establecerRespuesta(contratos);
        return response;
    }

    private List<ContratoResponse> obtenerTodosContratosPorGestion(ObtenerTodosContratosRequest request) {
        List<ContratoResponse> contratos=contratoRepository.findAllContratosPorGestion(request.getGestionContrato())
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
