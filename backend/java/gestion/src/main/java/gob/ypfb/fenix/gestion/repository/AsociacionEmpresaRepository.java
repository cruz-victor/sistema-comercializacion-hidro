/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.repository;

import gob.ypfb.fenix.gestion.domain.AsociacionEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AsociacionEmpresaRepository extends JpaRepository<AsociacionEmpresa, Long>, JpaSpecificationExecutor<AsociacionEmpresa> {
    @Query(value="select ae from AsociacionEmpresa ae where ae.idAsociacion=?1 and ae.idEmpresa=?2 and ae.eliminado=false")
    AsociacionEmpresa findById(Long idAsociacion, Long idEmpresa);
}