/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.partida;

import gob.ypfb.fenix.certificacion.service.partida.crear.CrearPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.modificar.ModificarPartidaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IPartidaControllerMapper {
    IPartidaControllerMapper INSTANCIA= Mappers.getMapper(IPartidaControllerMapper.class);

    CrearPartidaRequest requestToCrearPartidaRequest(DatosCrearPartidaRequest request);

    ModificarPartidaRequest requestToModificarPartidaRequest(DatosCrearPartidaRequest request);
}
