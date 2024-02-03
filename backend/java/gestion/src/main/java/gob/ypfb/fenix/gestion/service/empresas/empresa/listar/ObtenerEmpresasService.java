/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.listar;

import gob.ypfb.fenix.gestion.domain.Empresa;
import gob.ypfb.fenix.gestion.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObtenerEmpresasService implements IObtenerEmpresasService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Transactional(readOnly = true)
    public ObtenerEmpresasResponse obtenerEmpresas(ObtenerEmpresasRequest request) {
        List<Empresa> empresas = obtenerTodasEmpresas(request);
        ObtenerEmpresasResponse response = establecerRespuesta(empresas);

        return response;
    }

    private ObtenerEmpresasResponse establecerRespuesta(List<Empresa> empresas) {
        ObtenerEmpresasResponse response=new ObtenerEmpresasResponse();
        response.setEmpresas(empresas);
        return response;
    }

    private List<Empresa> obtenerTodasEmpresas(ObtenerEmpresasRequest request) {
        List<Empresa> empresas= empresaRepository.findAll(request.getEsAsociacion());
        return empresas;
    }
}
