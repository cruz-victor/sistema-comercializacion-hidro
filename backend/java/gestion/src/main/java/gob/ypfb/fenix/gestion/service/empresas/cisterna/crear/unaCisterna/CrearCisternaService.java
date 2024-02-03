/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import gob.ypfb.fenix.gestion.repository.CisternaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CrearCisternaService implements ICrearCisternaService {

    @Autowired
    CisternaRepository cisternaRepository;

    @Override
    @Transactional
    public CrearCisternaResponse crear(CrearCisternaRequest request) {
        Cisterna cisterna = mapearRequestCisterna(request);
        CrearCisternaResponse response = establecerRespuesta(cisterna);
        return response;
    }

    private CrearCisternaResponse establecerRespuesta(Cisterna cisterna) {
        CrearCisternaResponse response=new CrearCisternaResponse();
        response.setId(cisternaRepository.save(cisterna).getId());
        return response;
    }

    private Cisterna mapearRequestCisterna(CrearCisternaRequest request) {
        Cisterna cisterna=new Cisterna();
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
        cisterna.setFechaRegistro(LocalDate.now());
        cisterna.setEliminado(false);
        cisterna.setUsuarioAplicacion(request.getUsuarioAplicacion());
        cisterna.setJustificacion(request.getJustificacion());
        cisterna.setIpCliente(request.getIpCliente());
        cisterna.setHash(null);
        return cisterna;
    }
}
