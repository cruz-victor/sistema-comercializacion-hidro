/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.eliminar;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EliminarCisternaService implements IEliminarCisternaService {
    @Autowired
    CisternaRepository cisternaRepository;

    @Transactional
    public EliminarCisternaResponse eliminarLogicamente(EliminarCisternaRequest request) {
        Cisterna cisterna = bucarCisternaPorId(request);
        mapearRequestCisterna(request, cisterna);
        validarExistenciaContrato(cisterna);
        EliminarCisternaResponse response = establecerRespuesta(cisterna);

        return response;
    }

    private EliminarCisternaResponse establecerRespuesta(Cisterna cisterna) {
        EliminarCisternaResponse response=new EliminarCisternaResponse();
        response.setId(cisternaRepository.save(cisterna).getId());
        return response;
    }

    private void mapearRequestCisterna(EliminarCisternaRequest request, Cisterna cisterna) {
        cisterna.setJustificacion(request.getJustificacion());
        cisterna.setUsuarioAplicacion(request.getUsuario());
        cisterna.setEliminado(request.getEliminado());
    }

    private Cisterna bucarCisternaPorId(EliminarCisternaRequest request) {
        Cisterna cisterna= cisternaRepository.findById(request.getId()).orElse(new Cisterna());
        return cisterna;
    }

    private void validarExistenciaContrato(Cisterna cisterna) {
        if (cisterna.getId()==null){
            log.error("servicio - EliminarContratoService: "+"El objeto contrato es null");
            throw new RuntimeException("servicio - EliminarContratoService: "+"El objeto contrato es null");
        }
    }
}
