/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularCisternas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class DesvincularEmpresaCisternasRequest {
    private Long idEmpresa;
    private List<Long> idCisternas;
    private String motivo;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
    private Boolean eliminado;
}
