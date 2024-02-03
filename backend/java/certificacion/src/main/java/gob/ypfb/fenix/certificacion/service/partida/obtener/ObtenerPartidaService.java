/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.obtener;

import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObtenerPartidaService implements IObtenerPartidaService {
    @Autowired
    PartidaRepository partidaRepository;

    //Todo: Cambio la respuesta del servicio a un objeto
    @Transactional(readOnly = true)
    public ObtenerPartidaResponse obtenerPartidaPorId(ObtenerPartidaRequest request) {
        Partida partida = buscarPartidaPorId(request);
        ObtenerPartidaResponse response = establecerRespuesta(partida);
        return response;
    }

    private ObtenerPartidaResponse establecerRespuesta(Partida partida) {
        ObtenerPartidaResponse response= new ObtenerPartidaResponse();
        response.setPartida(partida);
        return response;
    }

    private Partida buscarPartidaPorId(ObtenerPartidaRequest request) {
        Partida partida= partidaRepository.findPartidaById(request.getId());
        return partida;
    }
}
