/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.eliminar;

import gob.ypfb.fenix.gestion.domain.Empresa;
import gob.ypfb.fenix.gestion.repository.EmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarEmpresaService implements IEliminarEmpresaService {
    @Autowired
    EmpresaRepository empresaRepository;

    @Transactional
    public EliminarEmpresaResponse eliminarLogicamente(EliminarEmpresaRequest request) {
        Empresa empresa = buscarEmpresaPorId(request);
        mapearRequestEmpresa(request, empresa);
        validarExistenciaEmpresa(empresa);
        EliminarEmpresaResponse response = establecerRespuesta(empresa);

        return response;
    }

    private EliminarEmpresaResponse establecerRespuesta(Empresa empresa) {
        EliminarEmpresaResponse response=new EliminarEmpresaResponse();
        response.setId(empresaRepository.save(empresa).getId());
        return response;
    }

    private void mapearRequestEmpresa(EliminarEmpresaRequest request, Empresa empresa) {
        empresa.setJustificacion(request.getJustificacion());
        empresa.setUsuarioAplicacion(request.getUsuario());
        empresa.setEliminado(request.getEliminado());
    }

    private Empresa buscarEmpresaPorId(EliminarEmpresaRequest request) {
        Empresa empresa= empresaRepository.findById(request.getId()).orElse(new Empresa());
        return empresa;
    }

    private void validarExistenciaEmpresa(Empresa empresa) {
        if (empresa.getId()==null){
            log.error("servicio - EliminarEmpresaService: "+"El objeto empresa es null");
            throw new RuntimeException("servicio - EliminarEmpresaService: "+"El objeto empresa es null");
        }
    }
}
