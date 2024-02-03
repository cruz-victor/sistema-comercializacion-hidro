/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularCisternas;

import gob.ypfb.fenix.gestion.domain.EmpresaCisterna;
import gob.ypfb.fenix.gestion.repository.EmpresaCisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesvincularEmpresaCisternasService implements IDesvincularEmpresaCisternasService {

    @Autowired
    EmpresaCisternaRepository empresaCisternaRepository;

    @Override
    @Transactional
    public DesvincularEmpresaCisternasResponse desvincular(DesvincularEmpresaCisternasRequest request) {
        List<EmpresaCisterna> empresaCisternas = obtenerCisternasDeLaEmpresa(request);
        empresaCisternaRepository.saveAll(empresaCisternas);
        DesvincularEmpresaCisternasResponse response = establecerRespuesta(request);

        return response;
    }

    private DesvincularEmpresaCisternasResponse establecerRespuesta(DesvincularEmpresaCisternasRequest request) {
        DesvincularEmpresaCisternasResponse response=new DesvincularEmpresaCisternasResponse();
        response.setId(request.getIdEmpresa());
        return response;
    }

    private List<EmpresaCisterna> obtenerCisternasDeLaEmpresa(DesvincularEmpresaCisternasRequest request) {
        List<EmpresaCisterna> empresaCisternas= request.getIdCisternas().stream()
                .map(idCisterna->{
                    EmpresaCisterna empresaCisterna=empresaCisternaRepository.findById(request.getIdEmpresa(), idCisterna);
                    empresaCisterna.setIdEmpresa(request.getIdEmpresa());
                    empresaCisterna.setIdCisterna(idCisterna);
                    empresaCisterna.setMotivo(request.getMotivo());
                    empresaCisterna.setFechaFin(LocalDate.now());
                    empresaCisterna.setEliminado(request.getEliminado());
                    empresaCisterna.setUsuarioAplicacion(request.getUsuarioAplicacion());
                    empresaCisterna.setJustificacion(request.getJustificacion());
                    empresaCisterna.setIpCliente(request.getIpCliente());
                    empresaCisterna.setHash(request.getHash());

                    return empresaCisterna;
                })
                .collect(Collectors.toList());
        return empresaCisternas;
    }
}
