/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.modificar;

import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModificarPartidaService implements IModificarPartidaService {
    @Autowired
    PartidaRepository partidaRepository;

    @Transactional
    public ModificarPartidaResponse modificar(ModificarPartidaRequest request) {
        Partida partida = buscarPartidaPorId(request);
        validarExistenciaIdPartida(partida);
        partida = mapearRequestPartida(request);
        ModificarPartidaResponse response = establecerRespuesta(partida);
        validarExistenciaPartidaDuplicadas(request);
        return response;
    }

    private ModificarPartidaResponse establecerRespuesta(Partida partida) {
        ModificarPartidaResponse response = new ModificarPartidaResponse();
        response.setId(partidaRepository.save(partida).getId());
        return response;
    }

    private Partida mapearRequestPartida(ModificarPartidaRequest request) {
        Partida partida;
        partida = IPartidaModificarServiceMapper.INSTANCIA.requestToPartidaPresupuestaria(request);
        return partida;
    }

    private Partida buscarPartidaPorId(ModificarPartidaRequest request) {
        Partida partida= partidaRepository.findPartidaById(request.getId());
        return partida;
    }

    private void validarExistenciaIdPartida(Partida partida) {
        if (partida.getId()==null)
        {
            throw new RuntimeException("El ID partida que intenta modificar no exite en la base de datos.");
        }
    }

    private void validarExistenciaPartidaDuplicadas(ModificarPartidaRequest request) {
        Integer totalPartidas = partidaRepository.contarPartidas(request.getGestion(), request.getCategoria(), request.getNumeroPartida());

        if (totalPartidas == 0) {
            throw new RuntimeException("No existe la partida de la gestion " + request.getGestion() + ", categoria " + request.getCategoria() + " y numero de partida " + request.getNumeroPartida() + " en la base de datos.");
        }
        if (totalPartidas > 1) {
            throw new RuntimeException("Existen varias partidas de la gestion " + request.getGestion() + ", categoria " + request.getCategoria() + " y numero de partida " + request.getNumeroPartida() + " en la base de datos.");
        }

    }
}
