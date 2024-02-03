/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.eliminar;

import gob.ypfb.fenix.certificacion.domain.Partida;
import gob.ypfb.fenix.certificacion.repository.PartidaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarPartidaService implements IEliminarPartidaService {
    @Autowired
    PartidaRepository partidaRepository;

    @Transactional
    public EliminarPartidaResponse eliminarLogicamente(EliminarPartidaRequest request) {
        Partida partida = buscarPartidaPorId(request);
        validarExistenciaPartida(partida);
        mapearRequestPartida(request, partida);
        EliminarPartidaResponse response = establecerRespuesta(partida);
        return response;
    }

    private EliminarPartidaResponse establecerRespuesta(Partida partida) {
        EliminarPartidaResponse response=new EliminarPartidaResponse();
        response.setId(partidaRepository.save(partida).getId());
        return response;
    }

    private void mapearRequestPartida(EliminarPartidaRequest request, Partida partida) {
        partida.setJustificacion(request.getJustificacion());
        partida.setUsuarioAplicacion(request.getUsuario());
        partida.setEliminado(request.getEliminado());
    }

    private Partida buscarPartidaPorId(EliminarPartidaRequest request) {
        Partida partida= partidaRepository.findPartidaById(request.getId());
        return partida;
    }

    private void validarExistenciaPartida(Partida partida) {
        if (partida.getId()==null){
            log.error("servicio - EliminarPartidaService: "+"El objeto partida es null");
            throw new RuntimeException("servicio - EliminarPartidaService: "+"El objeto partida es null");
        }
    }
}
