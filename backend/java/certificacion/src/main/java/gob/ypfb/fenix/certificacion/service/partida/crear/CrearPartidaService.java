/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.crear;

import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrearPartidaService implements ICrearPartidaService {
    @Autowired
    PartidaRepository partidaRepository;

    @Override
    @Transactional
    public CrearPartidaResponse crear(CrearPartidaRequest request) {
        validarExistenciaPartida(request);
        Partida partida = mapearRequestPartida(request);
        CrearPartidaResponse response = establecerRespuesta(partida);
        return response;
    }

    private CrearPartidaResponse establecerRespuesta(Partida partida) {
        CrearPartidaResponse response=new CrearPartidaResponse();
        response.setId(partidaRepository.save(partida).getId());
        return response;
    }

    private Partida mapearRequestPartida(CrearPartidaRequest request) {
        Partida partida = IPartidaCrearServiceMapper.INSTANCIA.requestToPartidaPresupuestaria(request);
        return partida;
    }

    private void validarExistenciaPartida(CrearPartidaRequest request) {
        Integer totalPartidas= partidaRepository.contarPartidas(request.getGestion(), request.getCategoria(), request.getNumeroPartida());
        if(totalPartidas>0)
        {
            throw new RuntimeException("La partida de la gestion "+ request.getGestion()+", categoria "+ request.getCategoria()+" y numero de partida "+ request.getNumeroPartida()+" ya existe en la base de datos.");
        }
    }
}
