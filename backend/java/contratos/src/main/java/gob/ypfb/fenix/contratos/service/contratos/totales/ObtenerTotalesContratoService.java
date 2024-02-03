/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.totales;

import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtenerTotalesContratoService implements IObtenerTotalesContratoService {

    @Autowired
    ContratoRepository contratoRepository;

    public ObtenerTotalesContratoResponse obtenerTotalesPorTipoContrato(ObtenerTotalesContratoRequest request) {
        String totalesDashboard = obtenerJsonTotalesContrato(request);
        ObtenerTotalesContratoResponse response = establecerRespuesta(totalesDashboard);
        return response;
    }

    private ObtenerTotalesContratoResponse establecerRespuesta(String totalesDashboard) {
        ObtenerTotalesContratoResponse response=new ObtenerTotalesContratoResponse();
        response.setTotalesDashboard(totalesDashboard);
        return response;
    }

    private String obtenerJsonTotalesContrato(ObtenerTotalesContratoRequest request) {
        String totalesDashboard=contratoRepository.obtenerTotalesDashboard(request.getTipo(), request.getAmbito(), request.getGestionContrato());
        return totalesDashboard;
    }
}
