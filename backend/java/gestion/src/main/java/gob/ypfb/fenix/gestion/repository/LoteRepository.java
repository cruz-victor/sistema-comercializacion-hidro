/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.repository;

import gob.ypfb.fenix.gestion.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long>, JpaSpecificationExecutor<Lote> {
    @Query(value="select l from Lote l where Upper(l.tipoLote)=?1 and l.gestion=?2 and l.eliminado=false")
    List<Lote> findByTipoLote(String tipoLote, Integer gestion);

    @Query(value = "select l from Lote l where l.lote=?1 and Upper(l.tipoLote)=?2 and l.gestion=?3 and l.eliminado=false")
    Lote findByNombreAndTipo(Integer lote, String tipoLote, Integer gestion);
}