/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.vincularCisternas;

import gob.ypfb.fenix.gestion.domain.EmpresaCisterna;
import gob.ypfb.fenix.gestion.repository.EmpresaCisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VincularEmpresaCisternasService implements IVincularEmpresaCisternasService {

    @Autowired
    EmpresaCisternaRepository empresaCisternaRepository;

    @Override
    @Transactional
    public VincularEmpresaCisternasResponse vincular(VincularEmpresaCisternasRequest request) {
        List<EmpresaCisterna> empresaCisternas = obtenerCistarnasDeLaEmpresa(request);
        empresaCisternaRepository.saveAll(empresaCisternas);
        VincularEmpresaCisternasResponse response = establecerRespuesta(request);

        return response;
    }

    private VincularEmpresaCisternasResponse establecerRespuesta(VincularEmpresaCisternasRequest request) {
        VincularEmpresaCisternasResponse response=new VincularEmpresaCisternasResponse();
        response.setId(request.getIdEmpresa());
        return response;
    }

    private List<EmpresaCisterna> obtenerCistarnasDeLaEmpresa(VincularEmpresaCisternasRequest request) {
        List<EmpresaCisterna> empresaCisternas= request.getIdCisternas().stream()
                .map(idCisterna->{
                    EmpresaCisterna empresaCisterna=new EmpresaCisterna();
                    empresaCisterna.setIdEmpresa(request.getIdEmpresa());
                    empresaCisterna.setIdCisterna(idCisterna);
                    empresaCisterna.setMotivo(request.getMotivo());
                    empresaCisterna.setFechaInicio(request.getFechaInicio());
                    empresaCisterna.setFechaFin(request.getFechaFin());
                    empresaCisterna.setFechaRegistro(LocalDate.now());
                    empresaCisterna.setEliminado(false);
                    empresaCisterna.setUsuarioAplicacion(request.getUsuarioAplicacion());
                    empresaCisterna.setIpCliente(request.getIpCliente());
                    empresaCisterna.setHash(request.getHash());

                    return empresaCisterna;
                })
                .collect(Collectors.toList());
        return empresaCisternas;
    }
}
