/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.crear;

import gob.ypfb.fenix.gestion.domain.Empresa;
import gob.ypfb.fenix.gestion.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CrearEmpresaService implements ICrearEmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    @Transactional
    public CrearEmpresaResponse crear(CrearEmpresaRequest request) {
        Empresa empresa = new Empresa();
        mapearRequestEmpresa(request, empresa);
        CrearEmpresaResponse response=new CrearEmpresaResponse();
        response.setId(empresaRepository.save(empresa).getId());

        return response;
    }

    private void mapearRequestEmpresa(CrearEmpresaRequest request, Empresa empresa) {
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
        empresa.setFechaRegistro(LocalDate.now());
        empresa.setEliminado(false);
        empresa.setUsuarioAplicacion(request.getUsuarioAplicacion());
        empresa.setJustificacion(null);
        empresa.setIpCliente(request.getIpCliente());
        empresa.setHash(request.getHash());
        empresa.setRepresentanteLegal(request.getRepresentanteLegal());
        empresa.setFechaInicio(request.getFechaInicio());
        empresa.setFechaFin(request.getFechaFin());
    }
}
