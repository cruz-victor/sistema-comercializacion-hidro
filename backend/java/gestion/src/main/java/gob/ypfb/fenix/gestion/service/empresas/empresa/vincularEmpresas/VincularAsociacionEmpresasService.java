/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.vincularEmpresas;

import gob.ypfb.fenix.gestion.domain.AsociacionEmpresa;
import gob.ypfb.fenix.gestion.repository.AsociacionEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VincularAsociacionEmpresasService implements IVincularAsociacionEmpresasService {

    @Autowired
    AsociacionEmpresaRepository asociacionEmpresaRepository;

    @Override
    @Transactional
    public VincularAsociacionEmpresasResponse vincular(VincularAsociacionEmpresasRequest request) {
        List<AsociacionEmpresa> empresaCisternas = obtenerCisternasDeLaEmpresa(request);
        asociacionEmpresaRepository.saveAll(empresaCisternas);
        VincularAsociacionEmpresasResponse response = establecerRespuesta(request);

        return response;
    }

    private VincularAsociacionEmpresasResponse establecerRespuesta(VincularAsociacionEmpresasRequest request) {
        VincularAsociacionEmpresasResponse response=new VincularAsociacionEmpresasResponse();
        response.setId(request.getIdAsociacion());
        return response;
    }

    private List<AsociacionEmpresa> obtenerCisternasDeLaEmpresa(VincularAsociacionEmpresasRequest request) {
        List<AsociacionEmpresa> empresaCisternas= request.getIdEmpresas().stream()
                .map(idEmpresa->{
                    AsociacionEmpresa asociacionEmpresa=new AsociacionEmpresa();
                    asociacionEmpresa.setIdAsociacion(request.getIdAsociacion());
                    asociacionEmpresa.setIdEmpresa(idEmpresa);
                    asociacionEmpresa.setFechaInicio(request.getFechaInicio());
                    asociacionEmpresa.setFechaFin(request.getFechaFin());
                    asociacionEmpresa.setFechaRegistro(LocalDate.now());
                    asociacionEmpresa.setEliminado(false);
                    asociacionEmpresa.setUsuarioAplicacion(request.getUsuarioAplicacion());
                    asociacionEmpresa.setIpCliente(request.getIpCliente());
                    asociacionEmpresa.setHash(request.getHash());

                    return asociacionEmpresa;
                })
                .collect(Collectors.toList());
        return empresaCisternas;
    }
}
