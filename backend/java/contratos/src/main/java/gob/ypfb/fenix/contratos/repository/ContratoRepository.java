/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.repository;

import gob.ypfb.fenix.contratos.domain.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long>, JpaSpecificationExecutor<Contrato> {
    @Query(value ="select c from Contrato c where c.id=?1 and c.idTipoContrato=?2 and c.eliminado=false")
    Optional<Contrato> findById(Long idContrato, Integer tipoContrato);

    @Query(value ="select c from Contrato c where c.idTipoContrato=?1 and c.numeroContrato=?2 and c.gestionContrato=?3 and c.eliminado=false")
    Optional<Contrato> findByCodigo(Integer tipoContrato, Integer numeroContrato, Integer gestionContrato);

    @Query(value = "select c from Contrato c where c.idTipoContrato=?1 and c.eliminado=false")
    List<Contrato> findAllContratosPorTipo(Integer tipoContrato);

    @Query(value = "select c from Contrato c where c.eliminado=false")
    List<Contrato> findAllContratos();

    @Query(value = "select c from Contrato c where c.idTipoContrato=?1 and c.gestionContrato=?2 and c.eliminado=false")
    List<Contrato> findAllContratosPorTipoGestion(Integer tipoContrato, Integer gestion);

    @Query(value = "select c from Contrato c where c.gestionContrato=?1 and c.eliminado=false")
    List<Contrato> findAllContratosPorGestion(Integer gestion);

    @Query(value = "select contrato.obtener_totales_dashboard(?1, ?2, ?3)", nativeQuery = true)
    String obtenerTotalesDashboard(String tipo, String ambito, Integer gestion);
}