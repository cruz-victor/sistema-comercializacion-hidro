export interface Parametro {
  id: string;  
  descripcion: string;  
}

export interface CertificacionPresupuestaria{
  id?: number
  concepto: string
  idPartida?: number
  fechaEmision: string
  numeroCertificacion: string
  descripcion: string
  montoMaximo?: number
  montoEjecutado?: number
  saldo?: number
  tipoServicio: string
  idProcesoContratacion?: number
  idContrato?: number
  idLotes: Array<number>
  rutaDocumentoAdjunto: string
  usuarioAplicacion: string
  justificacion: string
  ipCliente: string
  hash: string
  idCertificacion?: number
}

export interface DetalleCertificacion {
  id: number
  concepto: string
  idPartida: number
  fechaEmision: string
  numeroCertificacion: string
  descripcion: string
  montoMaximo: number
  montoEjecutado: number
  saldo: number
  tipoServicio: string
}

export interface PartidaPresupuestaria{
  id?: number
  gestion?: number
  categoria: string
  numeroPartida: string
  montoActual?: number
  montoAprobado?: number
  montoIncremento?: number
  saldo?: number
  rutaDocumentoAdjunto: string
  fechaInicio: string
  fechaFin: string
  usuarioAplicacion: string
  justificacion: string
  ipCliente: string
  hash: string
}

export interface DetallePartida{
  id: number
  categoria: string
  numeroPartida: string
  montoActual: number
  montoAprobado: number
  montoIncremento: number
  saldo: number
  // rutaDocumentoAdjunto: string
}