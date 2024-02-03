/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.variasCisternas;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.domain.EmpresaCisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import gob.ypfb.fenix.gestion.repository.EmpresaCisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrearCisternasService implements ICrearCisternasService {
    @Autowired
    CisternaRepository cisternaRepository;

    @Autowired
    EmpresaCisternaRepository empresaCisternaRepository;

    @Override
    @Transactional
    public CrearCisternasResponse crear(CrearCisternasRequest request) {
        eliminarCisternasPorIdEmpresa(request.getIdEmpresa());
        List<Cisterna> cisternas = obtenerCisternasDelRequest(request);
        List<Cisterna> cisternasGuardadas = cisternaRepository.saveAll(cisternas);
        List<EmpresaCisterna> empresaCisternas = guardarEmpresaCisternas(request.getIdEmpresa(), cisternasGuardadas);
        empresaCisternaRepository.saveAll(empresaCisternas);
        CrearCisternasResponse response=new CrearCisternasResponse();
        response.setId(1L);
        return response;
    }

    private List<EmpresaCisterna> guardarEmpresaCisternas(Long idEmpresa, List<Cisterna> cisternasGuardadas) {
        List<EmpresaCisterna> empresaCisternas= cisternasGuardadas.stream()
                .map(c->{
                    EmpresaCisterna empresaCisterna=new EmpresaCisterna();
                    empresaCisterna.setIdEmpresa(idEmpresa);
                    empresaCisterna.setIdCisterna(c.getId());
                    empresaCisterna.setMotivo("vinculacion");
                    empresaCisterna.setFechaInicio(LocalDate.now());
                    empresaCisterna.setFechaFin(null);
                    empresaCisterna.setFechaRegistro(LocalDate.now());
                    empresaCisterna.setEliminado(false);
                    empresaCisterna.setUsuarioAplicacion(c.getUsuarioAplicacion());
                    empresaCisterna.setJustificacion(c.getJustificacion());
                    empresaCisterna.setIpCliente(c.getIpCliente());
                    empresaCisterna.setHash(c.getHash());

                    return empresaCisterna;
                }).collect(Collectors.toList());

        return empresaCisternas;
    }

    private List<Cisterna> obtenerCisternasDelRequest(CrearCisternasRequest request) {
        List<Cisterna> cisternas = request.getCisternas().stream()
                .map(c ->{
                        Cisterna cisterna=new Cisterna();
                        cisterna.setId(c.getId());
                        cisterna.setAnio(c.getAnio());
                        cisterna.setPlaca(c.getPlaca());
                        cisterna.setMarca(c.getMarca());
                        cisterna.setColor(c.getColor());
                        cisterna.setCapacidad(c.getCapacidad());
                        cisterna.setNroCompartimientos(c.getNroCompartimientos());
                        cisterna.setNroChasis(c.getNroChasis());
                        cisterna.setDisposicionEjes(c.getDisposicionEjes());
                        cisterna.setFechaInicio(c.getFechaInicio());
                        cisterna.setFechaFin(c.getFechaFin());
                        cisterna.setFechaRegistro(LocalDate.now());
                        cisterna.setEliminado(false);
                        cisterna.setUsuarioAplicacion(c.getUsuarioAplicacion());
                        cisterna.setJustificacion(c.getJustificacion());
                        cisterna.setIpCliente(c.getIpCliente());
                        cisterna.setHash(null);

                        return cisterna;
                })
                .collect(Collectors.toList());
        return cisternas;
    }

    private void eliminarCisternasPorIdEmpresa(Long idEmpresa) {
        empresaCisternaRepository.deleteByIdEmpresa(idEmpresa);
    }
}
