/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.internacional.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Adenda {
    private LocalDate fecha;
    private String objeto;
    private List<CertificacionPresupuestaria> certificacionesPresupuestarias;
    private Vigencia vigencia;
    //private NominacionVolumen nominacionVolumen;
    //private Tramos tramos;
    private List<Lote> lotes;
    private MontoTotal monto;
    private List<Producto> productos;
}
