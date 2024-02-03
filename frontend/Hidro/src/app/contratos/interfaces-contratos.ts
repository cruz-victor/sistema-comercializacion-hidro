export interface Adenda {
  fecha: String
  objeto: String
  certificacionesPresupuestarias: Array<CertificacionPresupuestaria>  
  vigencia: {
    fechaFinalizacion: String
  }, 
  productos: Array<Producto>
  monto:{
    montoAdjudicado?: number
  }  
  lotes: Array<{
    lote?: Number
    tramos:{
      principales: Array<Tramo>
      secundarios: Array<Tramo>
    }
  }>
}

export interface Tramo {
  puntoRecepcion: string;  
  frontera: string;  
  puntoEntrega: string; 
  tarifa?: number
}  

export interface Parametro {
  id: string;  
  descripcion: string;  
}
  
 export interface ParametrosLote{
  lote?: Number,
  parametroOrigen: Array<Parametro>,
  parametroFrontera: Array<Parametro>,
  parametroDestino: Array<Parametro>
}
  
export  interface Lote {
  lote?: Number,
  tramos:{
    principales: Array<Tramo>,
    secundarios: Array<Tramo>
  }
}  

export interface CertificacionPresupuestaria{
  id: number,
  idProcesoContratacion?: number
  numero?: number
}

export interface Producto{
  producto: string
  porcentajeAdjudicado?: number
  volumenAdjudicado?: number
  mermaPermitida?: number
}


export interface Contrato{
  informacionGeneral: { 
    numeroContrato?: number
    idTemporal: string
    fechaSuscripcion: string
    gestion?: number
  }
  informacionEspecifica: {
    clase: string
    tipo: string
    ambito: string
    medio: string
    sector: string
    productos: Array<Producto>
    descripcionContrato: string
  }
  monto:{
    montoAdjudicado?: number
    saldo?: number
  }
  empresa: {
    id?: number
  }
  vigencia: {
    fechaFinalizacion: string
  }
  
  certificacionesPresupuestarias: Array<CertificacionPresupuestaria>

  informacionUsuario: {
    usuario: string
    ip: string
  }
  lotes: Array<Lote>
  adendas: Array<Adenda>

  totalVolumenesMonto: VolumenMontoTotales
}

export interface DataLote{
  lote: Lote,
  pOrigenes: Array<Parametro>,
  pFronteras: Array<Parametro>,
  pDestinos: Array<Parametro>
}

export interface VolumenContrato{
  producto: string
  porcentajeNominado?: number
  volumenNominado?: number
  mermaPermitida?: number
  volumenCargado?: number
  volumenDescargado?: number
}

export interface MontoContrato{
  montoAdjudicado?: number
  ejecutado?: number
  saldo?: number
}

export interface VolumenMontoTotales{
  volumen: Array<VolumenContrato>
  monto: MontoContrato
}
