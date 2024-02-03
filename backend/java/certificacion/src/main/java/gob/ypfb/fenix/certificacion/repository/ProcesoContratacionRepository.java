/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.repository;

import gob.ypfb.fenix.certificacion.domain.ProcesoContratacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcesoContratacionRepository extends JpaRepository<ProcesoContratacion, Long>, JpaSpecificationExecutor<ProcesoContratacion> {

}