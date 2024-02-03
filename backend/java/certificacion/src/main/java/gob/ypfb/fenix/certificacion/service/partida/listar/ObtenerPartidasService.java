/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.listar;

import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerPartidasService implements IObtenerPartidasService {
    @Autowired
    PartidaRepository partidaRepository;

    @Override
    public ObtenerPartidasResponse obtenerPartidas() {
        List<Partida> partidas = obtenerTodasPartidas();
        ObtenerPartidasResponse response = establecerRespuesta(partidas);
        return response;
    }

    private ObtenerPartidasResponse establecerRespuesta(List<Partida> partidas) {
        ObtenerPartidasResponse response=new ObtenerPartidasResponse();
        response.setPartidas(partidas);
        return response;
    }

    private List<Partida> obtenerTodasPartidas() {
        List<Partida> partidas= partidaRepository.findAll();
        return partidas;
    }
}
