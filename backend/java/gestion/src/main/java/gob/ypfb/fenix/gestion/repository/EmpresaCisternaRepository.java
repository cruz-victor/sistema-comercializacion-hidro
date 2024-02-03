/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.repository;

import gob.ypfb.fenix.gestion.domain.EmpresaCisterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmpresaCisternaRepository extends JpaRepository<EmpresaCisterna, Long>, JpaSpecificationExecutor<EmpresaCisterna> {
    @Query(value="update EmpresaCisterna ec set ec.eliminado=true, ec.justificacion='Eliminacion logica masiva' where ec.idEmpresa=?1")
    @Modifying
    void deleteByIdEmpresa(Long idEmpresa);

    @Query(value="select ec from EmpresaCisterna ec where ec.idEmpresa=?1 and ec.idCisterna=?2 and ec.eliminado=false")
    EmpresaCisterna findById(Long idEmpresa, Long idCisterna);
}