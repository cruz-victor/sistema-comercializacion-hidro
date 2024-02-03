/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.procesoContratacion.listar;

import gob.ypfb.fenix.certificacion.domain.ProcesoContratacion;
import gob.ypfb.fenix.certificacion.repository.ProcesoContratacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerProcesosService implements IObtenerProcesosService {
    @Autowired
    ProcesoContratacionRepository procesoContratacionRepository;

    @Override
    public ObtenerProcesosResponse obtenerProcesos() {
        List<ProcesoContratacion> procesos = obtenerTodosProcesosContratacion();
        ObtenerProcesosResponse response = establecerRespuesta(procesos);
        return response;
    }

    private ObtenerProcesosResponse establecerRespuesta(List<ProcesoContratacion> procesos) {
        ObtenerProcesosResponse response=new ObtenerProcesosResponse();
        response.setProcesos(procesos);
        return response;
    }

    private List<ProcesoContratacion> obtenerTodosProcesosContratacion() {
        List<ProcesoContratacion> procesos=procesoContratacionRepository.findAll();
        return procesos;
    }
}
