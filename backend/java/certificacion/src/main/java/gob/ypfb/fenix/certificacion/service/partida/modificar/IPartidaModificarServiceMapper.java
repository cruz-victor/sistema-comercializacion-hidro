/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.modificar;

import gob.ypfb.fenix.certificacion.domain.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IPartidaModificarServiceMapper {
    IPartidaModificarServiceMapper INSTANCIA= Mappers.getMapper(IPartidaModificarServiceMapper.class);

    Partida requestToPartidaPresupuestaria(ModificarPartidaRequest request);
}
