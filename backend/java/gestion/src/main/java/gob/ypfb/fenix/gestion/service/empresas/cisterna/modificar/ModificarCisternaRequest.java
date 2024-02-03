/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.cisterna.modificar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarCisternaRequest {
        private Long id;
        private Integer anio;
        private String placa;
        private String marca;
        private String color;
        private Integer capacidad;
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
