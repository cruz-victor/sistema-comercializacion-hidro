package gob.ypfb.fenix.contratos.service.contratos.crear;

import gob.ypfb.fenix.contratos.domain.Contrato;

import java.time.LocalDate;

public class ContratoMother {
    public static Contrato crear(){
        Contrato contrato=new Contrato();
        contrato.setIdTipoContrato(1);
        contrato.setNumeroContrato(123);
        contrato.setGestionContrato(2020);
        contrato.setContrato("[]");
        contrato.setFechaRegistro(LocalDate.now());
        contrato.setEliminado(true);
        contrato.setUsuarioAplicacion("vcruz");
        contrato.setJustificacion("Prueba");
        contrato.setIpCliente("10.1.1.1");
        contrato.setHash("fsdfsfdsffsd");

        return contrato;
    }
}
