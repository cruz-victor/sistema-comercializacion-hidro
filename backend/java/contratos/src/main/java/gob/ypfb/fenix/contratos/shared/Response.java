/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.shared;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Slf4j
public class Response {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    public ResponseEntity<?> retornarExcepcion(String origenAplicacion, Exception e) {
        Response responseMensaje = new Response();

        responseMensaje.setSuccess(false);
        responseMensaje.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseMensaje.setMessage(e.getMessage());
        responseMensaje.setData("{}");

        return new ResponseEntity<>(
                responseMensaje,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> retornarErroresValidacion(BindingResult resultadoValidacion) {
        Response responseMensaje = new Response();
        Map<String, Object> resultado=new HashMap<>();

        if(resultadoValidacion.hasErrors())
        {
            System.out.println("CertificacionController.crearCertificacion+2");

            List<String> errores= resultadoValidacion.getFieldErrors().stream()
                    .map(error->"Atributo:'"+error.getField()+"'. "+error.getDefaultMessage())
                    .collect(Collectors.toList());

            resultado.put("errores", errores);
            System.out.println("CertificacionController.crearCertificacion+3");

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.BAD_REQUEST.value());
            responseMensaje.setMessage("Existen errores en la petición.");
            responseMensaje.setData(resultado);
        }

        return new ResponseEntity<>(
                responseMensaje,
                HttpStatus.BAD_REQUEST);
    }
}
