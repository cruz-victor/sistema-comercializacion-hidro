/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.repository;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CertificacionRepository extends JpaRepository<Certificacion, Long>, JpaSpecificationExecutor<Certificacion> {

    @Query(value="select c from Certificacion c where c.id=?1 and c.eliminado=false")
    Optional<Certificacion> findById(Long id);

    @Query(value="select c from Certificacion c where c.eliminado=false and c.concepto=?1")
    List<Certificacion> findAll(String concepto);

    @Query(value="select c from Certificacion c where c.eliminado=false")
    List<Certificacion> findAll();

    @Query(value = "select certificacion.obtener_totales_certificaciones_dashboard(?1)", nativeQuery = true)
    String obtenerTotalesDashboard(Integer gestion);

    @Query(value="select c from Certificacion c where c.id=?1 and c.eliminado=false")
    Certificacion buscarCertificacionContratoPorId(Long id);

    @Query(value="update Certificacion c set c.eliminado=true  where c.id=?1 and c.eliminado=false")
    @Modifying
    void darBajaCertificacionContratoPorId(Long id);

    @Query(value="select c from Certificacion c where c.idPartida=?1 and  c.eliminado=false")
    List<Certificacion> obtenerCertificacionesPorIdPartida(Long idPartida);

    @Query(value="select count(c) from Certificacion c  where c.idPartida =?1 and c.concepto =?2 and c.fechaEmision=?3 and c.numeroCertificacion = ?4 and c.eliminado=false")
    Integer contarCertificaciones(Long idPartida, String concepto, LocalDate fechaEmision, String numeroCertificacion);

    @Query(value="select count(c) from Certificacion c  where c.concepto =?1 and c.fechaEmision =?2 and c.numeroCertificacion = ?3 and c.eliminado=false")
    Integer contarMovimientos(String concepto, LocalDate fechaEmision, String numeroCertificacion);
}