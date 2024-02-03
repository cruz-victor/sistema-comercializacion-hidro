/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.repository;

import gob.ypfb.fenix.gestion.domain.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long>, JpaSpecificationExecutor<Tramo> {

    @Query(value="select t from Tramo t where t.idLote=?1 and t.eliminado=false")
    List<Tramo> findByPorLote(Long idLote);

    @Query(value="delete from Tramo t where t.idLote=?1 and t.eliminado=false")
    @Modifying
    void deleteByIdLote(Long idLote);

    @Query(value="update Tramo t set t.eliminado=true, t.justificacion=?2, t.usuarioAplicacion=?3 where t.idLote=?1")
    @Modifying
    void deleteLogicalByIdLote(Long id, String justificacion, String usuario);
}