/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.crear;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class CrearContratoRequest {
    private Long id;
    private Integer idTipoContrato;
    private Integer numeroContrato;
    private Integer gestionContrato;
    private String usuarioAplicacion;
    private String ipCliente;
    private String jsonContrato;
    private Boolean vigente;
}
