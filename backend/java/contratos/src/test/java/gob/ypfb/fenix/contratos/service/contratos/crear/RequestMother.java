package gob.ypfb.fenix.contratos.service.contratos.crear;

public final class RequestMother {
    static CrearContratoRequest request;


    public static CrearContratoRequest crear(){
        request=new CrearContratoRequest();

        request.setIdTipoContrato(1);
        request.setNumeroContrato(5678);
        request.setGestionContrato(456);
        request.setUsuarioAplicacion("vcruz");
        request.setIpCliente("127.0.0.2");
        request.setJsonContrato("{\"informacionGeneral\":{\"numeroContrato\":1010101010,\"fechaSuscripcion\":\"2021-07-07\",\"gestion\":0},\"informacionEspecifica\":{\"clase\":\"string\",\"tipo\":\"string\",\"ambito\":\"string\",\"medio\":\"string\",\"sector\":\"string\",\"producto\":\"string\",\"descripcionContrato\":\"string\"},\"lote\":[0],\"vigencia\":{\"fechaFinalizacion\":\"2021-07-07\",\"criterioFinalizacion\":\"string\"},\"nominacionVolumen\":{\"porcentajeNominado\":0,\"volumenNominado\":0,\"volumenEjecutado\":0},\"certificacionPresupuestaria\":{\"id\":0,\"numero\":\"string\",\"montoNominado\":0,\"montoEjecutado\":0},\"informacionUsuario\":{\"usuario\":\"string\",\"ip\":\"string\"}}");

        return request;
    }
}
