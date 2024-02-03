/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.cisterna.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DatosCrearCisternaRequest {
    @Min(1900)
    private Integer anio;
    private String placa;
    private String marca;
    private String color;
    @Min(0)
    private Integer capacidad;
    @Min(0)
    private Integer nroCompartimientos;
    private String nroChasis;
    private String disposicionEjes;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
}
