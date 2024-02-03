/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.modificar;

import gob.ypfb.fenix.certificacion.domain.Certificacion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICertificacionModificarServiceMapper {
    ICertificacionModificarServiceMapper INSTANCIA= Mappers.getMapper(ICertificacionModificarServiceMapper.class);

    Certificacion requestToCertificacionPresupuestaria(ModificarCertificacionRequest request);
}
