/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.lotes.lote.crear.conTramos;

import gob.ypfb.fenix.gestion.domain.Lote;
import gob.ypfb.fenix.gestion.domain.Tramo;
import gob.ypfb.fenix.gestion.repository.LoteRepository;
import gob.ypfb.fenix.gestion.repository.TramoRepository;
import gob.ypfb.fenix.gestion.shared.Tramos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrearLoteService implements ICrearLoteService {
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    TramoRepository tramoRepository;

    @Override
    @Transactional
    public CrearLoteResponse crear(CrearLoteRequest request) {
        Lote loteCreado= crearLote(request);
        List<Tramo> tramosCreados = crearTramos(loteCreado, request.getLote().getTramos());
        CrearLoteResponse response=new CrearLoteResponse();
        response.setId(loteCreado.getId());

        return response;
    }

    private List<Tramo> crearTramos(Lote loteCreado, Tramos tramos) {
        borrarTramosExistentesDelLote(loteCreado);
        //Obtener tramos del request
        List<Tramo> tramosObtenidos= obtenerTramosDelRequest(loteCreado, tramos);
        List<Tramo> tramosGuardados= tramoRepository.saveAll(tramosObtenidos);
        return tramosGuardados;
    }

    private void borrarTramosExistentesDelLote(Lote loteCreado) {
        //Borrar los tramos del Lote mediante el ID
        tramoRepository.deleteByIdLote(loteCreado.getId());
    }

    private List<Tramo> obtenerTramosDelRequest(Lote loteCreado, Tramos tramos) {
        List<Tramo> tramosPrincipales=tramos.getPrincipales().stream()
                .map(t->{
                    Tramo tramo=new Tramo();
                    tramo.setIdLote(loteCreado.getId());
                    tramo.setEsPrincipal(true);
                    tramo.setPuntoRecepcion(t.getPuntoRecepcion());
                    tramo.setFrontera(t.getFrontera());
                    tramo.setPuntoDestino(t.getPuntoEntrega());
                    tramo.setTarifa(t.getTarifa());
                    tramo.setTiempoEstimado(new BigDecimal(0));
                    tramo.setFechaRegistro(LocalDate.now());
                    tramo.setEliminado(false);
                    tramo.setUsuarioAplicacion(loteCreado.getUsuarioAplicacion());
                    tramo.setJustificacion(loteCreado.getJustificacion());
                    tramo.setIpCliente(loteCreado.getIpCliente());
                    tramo.setHash(loteCreado.getHash());

                    return tramo;
                })
                .collect(Collectors.toList());

        List<Tramo> tramosSecundarios=tramos.getSecundarios().stream()
                .map(t->{
                    Tramo tramo=new Tramo();
                    tramo.setIdLote(loteCreado.getId());
                    tramo.setEsPrincipal(false);
                    tramo.setPuntoRecepcion(t.getPuntoRecepcion());
                    tramo.setFrontera(t.getFrontera());
                    tramo.setPuntoDestino(t.getPuntoEntrega());
                    tramo.setTarifa(t.getTarifa());
                    tramo.setTiempoEstimado(new BigDecimal(0));
                    tramo.setFechaRegistro(LocalDate.now());
                    tramo.setEliminado(false);
                    tramo.setUsuarioAplicacion(loteCreado.getUsuarioAplicacion());
                    tramo.setJustificacion(loteCreado.getJustificacion());
                    tramo.setIpCliente(loteCreado.getIpCliente());
                    tramo.setHash(loteCreado.getHash());

                    return tramo;
                })
                .collect(Collectors.toList());

        List<Tramo> tramosObtenidos=new ArrayList<>();
        tramosObtenidos.addAll(tramosPrincipales);
        tramosObtenidos.addAll(tramosSecundarios);

        return tramosObtenidos;
    }

    private Lote crearLote(CrearLoteRequest request) {
        //Verificar si existe el lote;
        Lote lote= loteRepository.findByNombreAndTipo(request.getLote().getLote(), request.getTipoLote(), request.getLote().getGestion());

        //si no existe crear
        if (lote == null){
            lote=new Lote();
            lote.setTipoLote(request.getTipoLote());
            lote.setLote(request.getLote().getLote());
            lote.setTipoProducto(null);
            lote.setSector(null);
            lote.setVolumenMaximo(null);
            lote.setFechaInicio(request.getLote().getFechaInicio());
            lote.setFechaFin(request.getLote().getFechaFin());
            lote.setGestion(request.getLote().getGestion());
            lote.setFechaRegistro(LocalDate.now());
            lote.setEliminado(false);
            lote.setUsuarioAplicacion(request.getLote().getUsuarioAplicacion());
            lote.setJustificacion(null);
            lote.setIpCliente(request.getLote().getIpCliente());
            lote.setHash(request.getLote().getHash());
        }else
        {
            lote.setFechaInicio(request.getLote().getFechaInicio());
            lote.setFechaFin(request.getLote().getFechaFin());
        }

        lote=loteRepository.save(lote);
        return lote;
    }
}
