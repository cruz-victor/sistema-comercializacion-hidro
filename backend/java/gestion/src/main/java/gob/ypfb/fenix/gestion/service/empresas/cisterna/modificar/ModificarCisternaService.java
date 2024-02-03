/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.modificar;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ModificarCisternaService implements IModificarCisternaService {

    @Autowired
    CisternaRepository cisternaRepository;

    @Transactional
    public ModificarCisternaResponse modificar(ModificarCisternaRequest request) {
        Cisterna cisterna = buscarCisternaPorId(request);
        validarExistenciaCisterna(cisterna);
        mapearRequestCisterna(request, cisterna);
        ModificarCisternaResponse response = establecerRespuesta(cisterna);

        return response;
    }

    private ModificarCisternaResponse establecerRespuesta(Cisterna cisterna) {
        ModificarCisternaResponse response=new ModificarCisternaResponse();
        response.setId(cisternaRepository.save(cisterna).getId());
        return response;
    }

    private void mapearRequestCisterna(ModificarCisternaRequest request, Cisterna cisterna) {
        cisterna.setAnio(request.getAnio());
        cisterna.setPlaca(request.getPlaca());
        cisterna.setMarca(request.getMarca());
        cisterna.setColor(request.getColor());
        cisterna.setCapacidad(request.getCapacidad());
        cisterna.setNroCompartimientos(request.getNroCompartimientos());
        cisterna.setNroChasis(request.getNroChasis());
        cisterna.setDisposicionEjes(request.getDisposicionEjes());
        cisterna.setFechaInicio(request.getFechaInicio());
        cisterna.setFechaFin(request.getFechaFin());
        cisterna.setUsuarioAplicacion(request.getUsuarioAplicacion());
        cisterna.setJustificacion(request.getJustificacion());
        cisterna.setIpCliente(request.getIpCliente());
        cisterna.setHash(request.getHash());
    }

    private Cisterna buscarCisternaPorId(ModificarCisternaRequest request) {
        Cisterna cisterna= cisternaRepository.findById(request.getId()).orElse(new Cisterna());
        return cisterna;
    }

    private void validarExistenciaCisterna(Cisterna cisterna) {
        if (cisterna.getId()==null)
        {
            log.error("servicio - ModificarCisternaService: "+"El objeto cisterna es null");
            throw new RuntimeException("servicio - ModificarCisternaService: "+"El objeto cisterna es null");
        }
    }
}
