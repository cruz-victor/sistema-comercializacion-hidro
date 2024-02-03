/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.parametros.repository;

import gob.ypfb.fenix.parametros.domain.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {

    @Query("select p from Parametro p where p.contexto = ?1 and p.fechaFin is null")
    List<Parametro> findParametrosPorTipo(String contexto);
}