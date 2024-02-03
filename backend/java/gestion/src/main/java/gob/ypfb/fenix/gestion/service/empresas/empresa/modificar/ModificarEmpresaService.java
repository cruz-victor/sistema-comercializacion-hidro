/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.modificar;

import gob.ypfb.fenix.gestion.domain.Empresa;
import gob.ypfb.fenix.gestion.repository.EmpresaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModificarEmpresaService implements IModificarEmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Transactional
    public ModificarEmpresaResponse modificar(ModificarEmpresaRequest request) {
        Empresa empresa = buscarEmpresaPorId(request);
        validarExistenciaEmpresa(empresa);
        mapearRequestEmpresa(request, empresa);
        ModificarEmpresaResponse response = establecerRespuesta(empresa);

        return response;
    }

    private ModificarEmpresaResponse establecerRespuesta(Empresa empresa) {
        ModificarEmpresaResponse response=new ModificarEmpresaResponse();
        response.setId(empresaRepository.save(empresa).getId());
        return response;
    }

    private void mapearRequestEmpresa(ModificarEmpresaRequest request, Empresa empresa) {
        empresa.setRazonSocial(request.getRazonSocial());
        empresa.setPais(request.getPais());
        empresa.setEsAsociacion(request.getEsAsociacion());
        empresa.setTipoDocumento(request.getTipoDocumento());
        empresa.setNumeroDocumento(request.getNumeroDocumento());
        empresa.setTipoConstitucion(request.getTipoConstitucion());
        empresa.setEmail(request.getEmail());
        empresa.setDireccion(request.getDireccion());
        empresa.setSitioWeb(request.getSitioWeb());
        empresa.setTelefono(request.getTelefono());
        empresa.setUsuarioAplicacion(request.getUsuarioAplicacion());
        empresa.setJustificacion(request.getJustificacion());
        empresa.setIpCliente(request.getIpCliente());
        empresa.setHash(request.getHash());
        empresa.setRepresentanteLegal(request.getRepresentanteLegal());
        empresa.setFechaInicio(request.getFechaInicio());
        empresa.setFechaFin(request.getFechaFin());
    }

    private Empresa buscarEmpresaPorId(ModificarEmpresaRequest request) {
        Empresa empresa= empresaRepository.findById(request.getId()).orElse(new Empresa());
        return empresa;
    }

    private void validarExistenciaEmpresa(Empresa empresa) {
        if (empresa.getId()==null)
        {
            log.error("servicio - ModificarEmpresaService: "+"El objeto empresa es null");
            throw new RuntimeException("servicio - ModificarEmpresaService: "+"El objeto empresa es null");
        }
    }
}
