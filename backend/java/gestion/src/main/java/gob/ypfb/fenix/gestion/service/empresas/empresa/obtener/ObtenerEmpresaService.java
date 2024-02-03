/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.obtener;

import gob.ypfb.fenix.gestion.domain.Empresa;
import gob.ypfb.fenix.gestion.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObtenerEmpresaService implements IObtenerEmpresaService {
    @Autowired
    EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public ObtenerEmpresaResponse obtenerEmpresaPorId(ObtenerEmpresaRequest request) {
        Empresa empresa=empresaRepository.findById(request.getId()).orElse(new Empresa());
        ObtenerEmpresaResponse response = establecerRespuesta(empresa);

        return response;
    }

    private ObtenerEmpresaResponse establecerRespuesta(Empresa empresa) {
        ObtenerEmpresaResponse response= new ObtenerEmpresaResponse();
        response.setEmpresa(empresa);
        return response;
    }
}
