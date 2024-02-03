/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.repository;

import gob.ypfb.fenix.certificacion.domain.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long>, JpaSpecificationExecutor<Partida> {
    @Query(value="select p from Partida p where p.eliminado=false")
    List<Partida> findAll();

    @Query(value="select p from Partida p where p.id=?1 and p.eliminado=false")
    Partida findPartidaById(Long id);

    @Query(value = "select certificacion.obtener_totales_partidas_dashboard(?1)", nativeQuery = true)
    String obtenerTotalesDashboard(Integer gestion);

    @Query(value="select count(p) from Partida p  where  p.gestion =?1 and  p.categoria = ?2 and p.numeroPartida = ?3 and p.eliminado =false")
    Integer contarPartidas(Integer gestion, String categoria, String numeroPartida);
}