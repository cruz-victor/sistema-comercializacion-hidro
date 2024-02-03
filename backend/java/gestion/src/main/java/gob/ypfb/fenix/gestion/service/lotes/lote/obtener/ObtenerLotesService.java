/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.lotes.lote.obtener;

import gob.ypfb.fenix.gestion.domain.Lote;
import gob.ypfb.fenix.gestion.repository.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerLotesService implements IObtenerLotesService{
    @Autowired
    LoteRepository loteRepository;

    @Override
    public ObtenerLotesResponse obtenerLotesPorTipo(ObtenerLotesRequest request) {
        List<Lote> lotes= loteRepository.findByTipoLote(request.getTipoLote(), request.getGestion());
        ObtenerLotesResponse response=new ObtenerLotesResponse();
        response.setLotes(lotes);
        return response;
    }
}
