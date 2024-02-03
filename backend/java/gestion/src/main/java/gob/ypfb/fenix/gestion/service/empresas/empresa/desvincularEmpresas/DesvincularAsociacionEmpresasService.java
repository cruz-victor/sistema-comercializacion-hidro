/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularEmpresas;

import gob.ypfb.fenix.gestion.domain.AsociacionEmpresa;
import gob.ypfb.fenix.gestion.repository.AsociacionEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesvincularAsociacionEmpresasService implements IDesvincularAsociacionEmpresasService {

    @Autowired
    AsociacionEmpresaRepository asociacionEmpresaRepository;

    @Override
    @Transactional
    public DesvincularAsociacionEmpresasResponse desvincular(DesvincularAsociacionEmpresasRequest request) {
        List<AsociacionEmpresa> asociacionEmpresas = obtenerEmpresas(request);
        asociacionEmpresaRepository.saveAll(asociacionEmpresas);
        DesvincularAsociacionEmpresasResponse response = establecerRespuesta(request);

        return response;
    }

    private DesvincularAsociacionEmpresasResponse establecerRespuesta(DesvincularAsociacionEmpresasRequest request) {
        DesvincularAsociacionEmpresasResponse response=new DesvincularAsociacionEmpresasResponse();
        response.setId(request.getIdAsociacion());
        return response;
    }

    private List<AsociacionEmpresa> obtenerEmpresas(DesvincularAsociacionEmpresasRequest request) {
        List<AsociacionEmpresa> asociacionEmpresas= request.getIdEmpresas().stream()
                .map(idEmpresa->{
                    AsociacionEmpresa asociacionEmpresa= asociacionEmpresaRepository.findById(request.getIdAsociacion(), idEmpresa);
                    asociacionEmpresa.setIdAsociacion(request.getIdAsociacion());
                    asociacionEmpresa.setIdEmpresa(idEmpresa);
                    asociacionEmpresa.setFechaFin(LocalDate.now());
                    asociacionEmpresa.setEliminado(request.getEliminado());
                    asociacionEmpresa.setUsuarioAplicacion(request.getUsuarioAplicacion());
                    asociacionEmpresa.setJustificacion(request.getJustificacion());
                    asociacionEmpresa.setIpCliente(request.getIpCliente());
                    asociacionEmpresa.setHash(request.getHash());

                    return asociacionEmpresa;
                })
                .collect(Collectors.toList());
        return asociacionEmpresas;
    }
}
