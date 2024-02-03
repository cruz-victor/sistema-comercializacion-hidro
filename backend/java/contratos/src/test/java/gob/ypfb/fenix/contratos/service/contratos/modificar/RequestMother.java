package gob.ypfb.fenix.contratos.service.contratos.modificar;

public final class RequestMother {
    static ModificarContratoRequest request;

    public static ModificarContratoRequest crear(){
        request=new ModificarContratoRequest();

        request.setId(27L);
        request.setIdTipoContrato(1);
        request.setNumeroContrato(1233);
        request.setGestionContrato(2021);
        request.setUsuarioAplicacion("vcruzz");
        request.setIpCliente("127.0.0.3");
        request.setJsonContrato("{\"idTipoContrato\":111,\"tipoContrato\":\"TRANSPORTE\",\"servicio\":\"string\",\"numeroContrato\":\"string\",\"fechaSuscripcion\":\"2021-01-01\",\"plazoVigencia\":{\"plazo\":\"2021-01-01\",\"vigencia\":\"string\"},\"certificacion\":{\"idCertifcacionPresupuestaria\":111,\"certificacionPresupuestaria\":123,\"tipoContratoCertificacion\":\"string\"},\"empresa\":[{\"idEmpresa\":111,\"nombreEmpresa\":\"string\",\"representanteLegal\":\"string\",\"nit\":\"string\"}],\"lote\":{\"idLote\":111,\"loteNombre\":\"string\",\"tramos\":{\"principales\":[{\"numero\":\"string\",\"puntoRecepcion\":\"string\",\"puntoEntrega\":\"string\",\"tarifa\":123}],\"secundarios\":[{\"numero\":\"string\",\"puntoRecepcion\":\"string\",\"puntoEntrega\":\"string\",\"tarifa\":123}]}},\"nominacionVolumen\":{\"porcentajePeriodoOperacion\":123,\"volumenMaximo\":123,\"volumenTransportado\":123},\"merma\":{\"producto\":\"string\",\"maximaPermisible\":123},\"garantia\":{\"retencion\":7,\"boletaGarantia\":[{\"idBoletaGarantia\":123,\"identificador\":\"string\",\"emisor\":\"string\",\"monto\":123,\"vencimiento\":\"2021-01-01\"}]},\"camionesCisterna\":[{\"idCamionCisterna\":111,\"cantidad\":123,\"detallePlacas\":{\"numero\":123,\"numeroPlaca\":\"string\"}}],\"adendas\":[{\"idAdenda\":111,\"fecha\":\"2021-01-01\",\"objeto\":\"string\",\"certificacion\":{\"idCertifcacionPresupuestaria\":111,\"certificacionPresupuestaria\":123,\"tipoContratoCertificacion\":\"string\"},\"plazoVigencia\":{\"plazo\":\"2021-01-01\",\"criterioPlazo\":\"ultima descarga\",\"criterioVigencia\":\"emision acta de cierre\"},\"lote\":{\"idLote\":111,\"loteNombre\":\"string\",\"tramos\":{\"principales\":[{\"numero\":\"string\",\"puntoRecepcion\":\"string\",\"puntoEntrega\":\"string\",\"tarifa\":123}],\"secundarios\":[{\"numero\":\"string\",\"puntoRecepcion\":\"string\",\"puntoEntrega\":\"string\",\"tarifa\":123}]}}}]}");

        return request;
    }
}
