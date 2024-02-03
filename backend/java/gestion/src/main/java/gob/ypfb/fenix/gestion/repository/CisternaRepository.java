/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.repository;

import gob.ypfb.fenix.gestion.domain.Cisterna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CisternaRepository extends JpaRepository<Cisterna, Long>, JpaSpecificationExecutor<Cisterna> {
    @Query(value="select c from EmpresaCisterna ec join Cisterna c on ec.idCisterna =c.id where ec.idEmpresa =?1 and ec.eliminado =false and c.eliminado =false")
    List<Cisterna> findAllPorEmpresa(Long idEmpresa);

    @Query(value="select c from Cisterna c where c.eliminado=false")
    List<Cisterna> findAll();

    @Query(value = "select c from Cisterna c where c.id=?1 and c.eliminado=false")
    Optional<Cisterna> findById(Long id);
}