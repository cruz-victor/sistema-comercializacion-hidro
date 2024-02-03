/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.obtener;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObtenerCisternaService implements IObtenerCisternaService {
    @Autowired
    CisternaRepository cisternaRepository;

    @Transactional(readOnly = true)
    public ObtenerCisternaResponse obtenerCisternaPorId(ObtenerCisternaRequest request) {
        Cisterna cisterna = buscarCisternaPorId(request);
        ObtenerCisternaResponse response = establecerRespuesta(cisterna);

        return response;
    }

    private ObtenerCisternaResponse establecerRespuesta(Cisterna cisterna) {
        ObtenerCisternaResponse response= new ObtenerCisternaResponse();
        response.setCisterna(cisterna);
        return response;
    }

    private Cisterna buscarCisternaPorId(ObtenerCisternaRequest request) {
        Cisterna cisterna=cisternaRepository.findById(request.getId()).orElse(new Cisterna());
        return cisterna;
    }
}
