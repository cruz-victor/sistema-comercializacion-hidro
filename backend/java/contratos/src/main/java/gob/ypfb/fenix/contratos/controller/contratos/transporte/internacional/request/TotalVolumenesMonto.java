/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.internacional.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TotalVolumenesMonto {
    private List<Volumen> volumen;
    private Monto monto;
}
