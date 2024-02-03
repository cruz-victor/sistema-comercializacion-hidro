/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.lotes.lote.eliminar;

import gob.ypfb.fenix.gestion.domain.Lote;
import gob.ypfb.fenix.gestion.repository.LoteRepository;
import gob.ypfb.fenix.gestion.repository.TramoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarLoteService implements IEliminarLoteService {
    @Autowired
    LoteRepository loteRepository;

    @Autowired
    TramoRepository tramoRepository;

    @Transactional
    public EliminarLoteResponse eliminarLogicamente(EliminarLoteRequest request) {
        Lote Lote = eliminarLogicamenteLote(request);
        Boolean resultadoEliminacion= eliminarLogicamenteTramos(request);
        EliminarLoteResponse response=new EliminarLoteResponse();
        response.setId(resultadoEliminacion?1:0);

        return response;
    }

    private Boolean eliminarLogicamenteTramos(EliminarLoteRequest request) {
        tramoRepository.deleteLogicalByIdLote(request.getId(), request.getJustificacion(), request.getUsuario());
        return true;
    }

    private Lote eliminarLogicamenteLote(EliminarLoteRequest request) {
        Lote lote= loteRepository.findById(request.getId()).orElse(new Lote());

        validarExistenciaLote(lote);

        lote.setJustificacion(request.getJustificacion());
        lote.setUsuarioAplicacion(request.getUsuario());
        lote.setEliminado(request.getEliminado());

        lote=loteRepository.save(lote);

        return lote;
    }

    private void validarExistenciaLote(Lote lote) {
        if (lote.getId()==null){
            log.error("servicio - EliminarloteService: "+"No existe el lote que intenta eliminar");
            throw new RuntimeException("servicio - EliminarLoteService: "+"No existe el lote que intenta eliminar");
        }
    }
}
