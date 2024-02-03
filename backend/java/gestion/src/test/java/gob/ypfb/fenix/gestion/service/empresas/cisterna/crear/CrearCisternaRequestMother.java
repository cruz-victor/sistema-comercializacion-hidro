package gob.ypfb.fenix.gestion.service.empresas.cisterna.crear;

import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.CrearCisternaRequest;

import java.time.LocalDate;

public class CrearCisternaRequestMother {

    public static CrearCisternaRequest crear() {
        CrearCisternaRequest request=new CrearCisternaRequest();
        //request.setIdEmpresa(1l);
        request.setPlaca("ABC");
        request.setMarca("ABC");
        request.setColor("ABC");
        request.setCapacidad(1);
        request.setNroCompartimientos(5);
        request.setNroChasis("ABC");
        request.setDisposicionEjes("ABC");
        request.setFechaInicio(LocalDate.now());
        request.setFechaFin(null);
        request.setEstado("AC");
        request.setUsuarioAplicacion("VCRUZ");
        request.setJustificacion(null);
        request.setIpCliente(null);
        
        return request;
    }
}
