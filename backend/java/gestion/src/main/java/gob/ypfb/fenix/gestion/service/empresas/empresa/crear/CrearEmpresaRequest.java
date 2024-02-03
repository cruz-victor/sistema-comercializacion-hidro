/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.crear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CrearEmpresaRequest {
    private Long id;
    private String razonSocial;
    private String pais;
    private Boolean esAsociacion;
    private String tipoDocumento;
    private String numeroDocumento;
    private String tipoConstitucion;
    private String email;
    private String direccion;
    private String sitioWeb;
    private String telefono;
    private LocalDate fechaRegistro;
    private Boolean eliminado;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
    private String representanteLegal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
