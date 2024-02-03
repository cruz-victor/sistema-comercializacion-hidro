/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.totales;

import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtenerTotalesPartidaService implements IObtenerTotalesPartidaService {

    @Autowired
    PartidaRepository partidaRepository;

    public ObtenerTotalesPartidaResponse obtenerTotales(ObtenerTotalesPartidaRequest request) {
        String totalesDashboard = obtenerJsonTotalesPartidas(request);
        ObtenerTotalesPartidaResponse response = establecerRespuesta(totalesDashboard);
        return response;
    }

    private ObtenerTotalesPartidaResponse establecerRespuesta(String totalesDashboard) {
        ObtenerTotalesPartidaResponse response=new ObtenerTotalesPartidaResponse();
        response.setTotalesDashboard(totalesDashboard);
        return response;
    }

    private String obtenerJsonTotalesPartidas(ObtenerTotalesPartidaRequest request) {
        String totalesDashboard= partidaRepository.obtenerTotalesDashboard(request.getGestion());
        return totalesDashboard;
    }
}
