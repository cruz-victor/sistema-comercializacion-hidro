/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.lotes.tramo.obtener;

import gob.ypfb.fenix.gestion.domain.Tramo;
import gob.ypfb.fenix.gestion.repository.TramoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTramosService implements IObtenerTramosService {
    @Autowired
    TramoRepository tramoRepository;

    @Override
    public ObtenerTramosResponse obtenerTramosPorLote(ObtenerTramosPorLoteRequest request) {
        List<Tramo> tramos= tramoRepository.findByPorLote(request.getIdLote());
        ObtenerTramosResponse response=new ObtenerTramosResponse();
        response.setTramos(tramos);

        return response;
    }
}
