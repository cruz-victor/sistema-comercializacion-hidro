/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.repository;

import gob.ypfb.fenix.contratos.domain.VolumenEjecutado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VolumenEjecutadoRepository extends JpaRepository<VolumenEjecutado, Long>, JpaSpecificationExecutor<VolumenEjecutado> {

}