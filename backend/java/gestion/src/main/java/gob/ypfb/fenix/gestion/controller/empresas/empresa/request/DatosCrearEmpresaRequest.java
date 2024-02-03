/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.empresa.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DatosCrearEmpresaRequest {
    private String razonSocial;
    private String pais;
    @Pattern(regexp = "((CI|NIT|PAS))", message= "El valor del tipo de documento no está en el rango opcional (CI,NIT,PAS)")
    private String tipoDocumento;
    private String numeroDocumento;
    private String tipoConstitucion;
    @Email
    private String email;
    private String direccion;
    private String sitioWeb;
    private String telefono;
    private String representanteLegal;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
}
