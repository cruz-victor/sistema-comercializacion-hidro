/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.listar;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObtenerCisternasService implements IObtenerCisternasService {

    @Autowired
    CisternaRepository cisternaRepository;

    @Transactional(readOnly = true)
    public ObtenerCisternasResponse obtenerCisternas() {
        List<Cisterna> cisternas = obtenerTodasCisternas();
        ObtenerCisternasResponse response = establecerRespuesta(cisternas);

        return response;
    }

    private ObtenerCisternasResponse establecerRespuesta(List<Cisterna> cisternas) {
        ObtenerCisternasResponse response = new ObtenerCisternasResponse();
        response.setCisternas(cisternas);
        return response;
    }

    private List<Cisterna> obtenerTodasCisternas() {
        List<Cisterna> cisternas=cisternaRepository.findAll();
        return cisternas;
    }

    @Transactional(readOnly = true)
    public ObtenerCisternasResponse obtenerCisternasPorEmpresa(ObtenerCisternasPorEmpresaRequest request) {
        List<Cisterna> cisternas=cisternaRepository.findAllPorEmpresa(request.getIdEmpresa());
        ObtenerCisternasResponse response = establecerRespuesta(cisternas);

        return response;
    }
}
