import { Component, OnInit, Input} from '@angular/core';

import { ParametricasService } from "../../../app//parametricas.service";
import { ContratosService } from "../../../app/contratos.service";
import { CertificacionesService } from "../../certificaciones.service";
import { EmpresasService } from "../../empresas.service";

import { Tramo, Lote, Adenda, Parametro, Producto, Contrato, DataLote, VolumenContrato, ParametrosLote} from "../interfaces-contratos";

import { Toaster } from "ngx-toast-notifications";

import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../../ngb-date-i18n";

@Component({
  selector: 'app-contrato-suministro',
  templateUrl: './contrato-suministro.component.html',
  styleUrls: ['./contrato-suministro.component.css'],
  providers: [
    { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter },
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]  
})
export class ContratoSuministroComponent implements OnInit {
  
  @Input() idContract!: any;

  gestionActual = 2021

  justificacionCambio: string = ''

  // Main Object
  data!: Contrato;

  constructor(private empresasService: EmpresasService, 
              private parametricasService: ParametricasService, 
              private contratosService: ContratosService,
              private certificacionesService: CertificacionesService,
              private toaster: Toaster) {
  }

  // Date treatment
  fechaSuscripcion!: NgbDateStruct;
  fechaFinalizacion!: NgbDateStruct;

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }

  buildDateSuscripcion(objDate:any) {
    this.data.informacionGeneral.fechaSuscripcion = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  buildDateFinalizacion(objDate:any){
    this.data.vigencia.fechaFinalizacion = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  backBuildDateSuscripcion(){
    if (this.data.informacionGeneral.fechaSuscripcion){
      var date =  this.data.informacionGeneral.fechaSuscripcion
      this.fechaSuscripcion = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }

  backBuildDateFinalizacion(){
    if (this.data.vigencia.fechaFinalizacion){
      var date =  this.data.vigencia.fechaFinalizacion
      this.fechaFinalizacion = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }

  // local parametrics
  sectores: any;
  criteriosFinalizacion: any;
  mediosSuministro: any;
  plotes: any;

  isNew: boolean = true;
 
  parametrica: any;

  empresas: any;  
  asociaciones: any;

  empresasAsociaciones: Array<Parametro> = []; 
  nuevoParametro: any = {}; 

  ngOnInit(): void {
    this.getLotesTransporteInternacional()
    
    
    if (+this.idContract > 0){
      this.isNew = false;
      this.contratosService.obtenerContratoSuministro(+this.idContract)
        .subscribe(
          (result: any) => {
            var datum = result; 
            if (datum.success && datum.data.contrato) {
              this.data = datum.data.contrato;

              this.arrayLotecillos = this.data.lotes;
              
              // this.adendas = this.data.adendas;

              this.backBuildDateSuscripcion()
              this.backBuildDateFinalizacion()

              // abra que backbuildear fechas de adendas....un forecha...y pasarlos como parametro a adenda segment

              var aux = this.data.lotes.map((element:any)=>element.lote).forEach((lote:any) => {
                this.lotesContexto.forEach((loteContexto: any, index)=>{
                  if (loteContexto.id == lote) loteContexto.checked = true
                })
              });
              // console.log(this.lotesContexto)
            }
        },
        error => {
          console.log('Retrieving problems!');
        }
      );
    }

    this.getEmpresas()
    this.getSectores()
    this.getProductos()

    this.getAllCertificaciones()

    this.initDataContrato()

  } // fin del ngOnInit

  initDataContrato(){
    this.data = {
      informacionGeneral: { 
        numeroContrato: undefined,
        idTemporal: '',
        fechaSuscripcion: '',
        gestion: (new Date()).getFullYear()
      },
      informacionEspecifica: {
        clase: "ADHE",
        tipo: "SUMIN",
        ambito: "INT",
        medio: '', // PLANTA o BUQUE
        sector: '',
        productos: [],
        descripcionContrato: ''
      },
      monto:{
        montoAdjudicado: undefined,
        saldo: undefined
      },
      empresa: {
        id: undefined
      },
      vigencia: {
        fechaFinalizacion: '',
      },      
      certificacionesPresupuestarias: [],    
      informacionUsuario: {
        usuario: '',
        ip: '',
      },
      lotes: this.arrayLotecillos,
      adendas: [],
    
      totalVolumenesMonto: {
        volumen: [],
        monto:{}
      }
    }
  }

  // Para Empresas

  getEmpresas(){
    this.empresasService.obtenerEmpresas()
    .subscribe(
      result => {
        var empresas:any = result; 
        if (empresas.success) {
          this.empresas = empresas.data.empresas;
          for (let index = 0; index < this.empresas.length; index++) {
            const element = this.empresas[index];
            this.nuevoParametro = {id: element.id, descripcion: element.razonSocial};  
            this.empresasAsociaciones.push(this.nuevoParametro); 
          }
        }
      },
      error => {console.log('Retrieving problems!');}
    );

  }
  
  // Para parametricas

  getMediosSuministro(){
    this.parametricasService.obtenerParametrica('MEDIOS_SUMINISTRO')
      .subscribe(
        result => {
          var medios:any = result; 
          if (medios.success) {
            this.mediosSuministro = medios.data;
            console.log('new medios usministro.... ',this.sectores)
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
  }

  getSectores(){
    this.parametricasService.obtenerParametrica('SECTORES_INTERNACIONALES')
      .subscribe(
        result => {
          var sectores:any = result; 
          if (sectores.success) {
            this.sectores = sectores.data;
            console.log('new sectores.... ',this.sectores)
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
  }

  // Para productos
  
  parametricaProductos: any;
  getProductos(){
    this.parametricasService.obtenerParametrica('PRODUCTOS_INTERNACIONALES')
      .subscribe( 
        result => {
          var datum: any = result;
          if (datum.success) {
            this.parametricaProductos = datum.data.map((element:any)=>({id: element.codigo, descripcion: element.valor}))
          }
      },
      error => {console.log('Retrieving problems!');}
    ); 
  }

  addRowProductos() {     
    var nuevoProducto = {producto: '', porcentajeAdjudicado: undefined, volumenAdjudicado: undefined, mermaPermitida: undefined};  
    this.data.informacionEspecifica.productos.push(nuevoProducto)
    return true;  
  } 

  deleteRowProductos(index: any) {  
    this.data.informacionEspecifica.productos.splice(index, 1);
    return true;
  } 

  // Para Lotes

  lotesTransInter: any;
  lotesContexto  = []
  getLotesTransporteInternacional(){
    this.empresasService.obtenerLotesTransporteInternacional(this.gestionActual)
    .subscribe(
      result => {
        var lotesTransInter = result; 
        if (lotesTransInter.success) {
          // this.lotesTransInter = lotesTransInter.data.lotes
          // this.lotesContexto = this.lotesTransInter.map((element:any)=> ({id: element.id, checked:false}))
          const lotesRecuperados = lotesTransInter.data.lotes
          this.lotesTransInter = lotesRecuperados.sort((a:any,b:any)=>{
            if (a.lote<b.lote) return -1
            return 1
          })
          this.lotesContexto = this.lotesTransInter.map((element:any)=> ({id: element.id, checked:false}))
        }
        console.log(this.lotesContexto)
      },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }

  getTramosOfLote(id: any): DataLote {
    var dataLote: DataLote
    dataLote = {lote: {lote:undefined, tramos: {principales: [], secundarios: []}},
                 pOrigenes: [], pFronteras: [], pDestinos: []}

    this.empresasService.obtenerTramosPorLote(id)
    .subscribe(
      result => {
        if (result.success) {
          var tramosLote = result.data.tramos

          var origenes =tramosLote.reduce((acc: any, cur: any)=>{
            if (acc[cur.puntoRecepcion]) acc[cur.puntoRecepcion]++; else acc[cur.puntoRecepcion]=1
            return acc
          },{});          
          for (var key in origenes) dataLote.pOrigenes.push({id: key, descripcion: key});

          var fronteras = tramosLote.reduce((acc: any, cur: any)=>{
            if (acc[cur.frontera]) acc[cur.frontera]++; else acc[cur.frontera]=1
            return acc
          },{});          
          for (var key in fronteras) dataLote.pFronteras.push({id: key, descripcion: key});

          var destino = tramosLote.reduce((acc: any, cur: any)=>{
            if (acc[cur.puntoDestino]) acc[cur.puntoDestino]++; else acc[cur.puntoDestino]=1
            return acc
          },{});          
          for (var key in destino) dataLote.pDestinos.push({id: key, descripcion: key});

          dataLote.lote.lote = id;

          dataLote.lote.tramos.principales = tramosLote.filter((element: any) => element.esPrincipal)
            .map((e:any)=>{return {puntoRecepcion: e.puntoRecepcion, frontera: e.frontera, puntoEntrega: e.puntoDestino, tarifa: e.tarifa}});

          dataLote.lote.tramos.secundarios = tramosLote.filter((element: any) => !element.esPrincipal)
            .map((e:any)=>{return {puntoRecepcion: e.puntoRecepcion, frontera: e.frontera, puntoEntrega: e.puntoDestino, tarifa: e.tarifa}});
        }
    },
    error => {
      console.log('Retrieving problems!');
    }
    );    

    return dataLote;
  }

  // lotecitillos = [0];
  // lotesFiltrados: any;

  onChangeLote(valor:any){
    // console.log(valor)
    this.lotesContexto.forEach((element:any)=>{
      if (element.id == valor) element.checked = !element.checked
    })

    this.updateChangesOnLotes()
  }

  updateChangesOnLotes(){
    
    this.lotesContexto.filter((element:any)=>element.checked)
      .forEach((elementLoteContexto:any)=>{
        var existe = this.arrayLotecillos.some((elementArrayLotecillos:any)=>elementArrayLotecillos.lote===elementLoteContexto.id)
        if (!existe){
          var dataLoteSeleccionado = this.getTramosOfLote(elementLoteContexto.id)

          this.arrayLotecillos.push(dataLoteSeleccionado.lote);

          this.arrayParametrosLotecillos.push({
            lote: elementLoteContexto.id,
            parametroOrigen: dataLoteSeleccionado.pOrigenes,
            parametroFrontera: dataLoteSeleccionado.pFronteras,
            parametroDestino: dataLoteSeleccionado.pDestinos
          })

          var loteAdenda: Lote = {lote: elementLoteContexto.id, tramos: {principales: [], secundarios: []}}
          this.arrayLotecillosAdenda.push(loteAdenda)
        }
      })

    this.lotesContexto.filter((element:any)=>!element.checked)
      .forEach((elementLoteContexto:any)=>{
        this.arrayLotecillos.forEach((element:any,index)=>{
          if (element.lote===elementLoteContexto.id){
            this.arrayLotecillos.splice(index, 1)
            this.arrayParametrosLotecillos.splice(index, 1)
            this.arrayLotecillosAdenda.splice(index, 1)
          } 
        })
      })
  }

  arrayLotecillos: Array<Lote> = []; 
  arrayParametrosLotecillos: Array<ParametrosLote> = [];
  arrayLotecillosAdenda: Array<Lote> = []

  addRowLotecillos(){
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
    var arrayTramosPral: Array<Tramo> = [];
    arrayTramosPral.push(nuevoTramo);

    var nuevoTramoSec = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
    var arrayTramosSec: Array<Tramo> = [];
    arrayTramosSec.push(nuevoTramoSec);

    var lotecillo: any = {};
    lotecillo = {lote: null, tramos: {principales: arrayTramosPral, secundarios: arrayTramosSec}};

    this.arrayLotecillos.push(lotecillo);

    return true;
  }

  addRowLotecillos2(numeroLote: any){
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
    var arrayTramosPral: Array<Tramo> = [];
    arrayTramosPral.push(nuevoTramo);

    var nuevoTramoSec = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
    var arrayTramosSec: Array<Tramo> = [];
    arrayTramosSec.push(nuevoTramoSec);

    var lotecillo: any = {};
    lotecillo = {lote: numeroLote, tramos: {principales: arrayTramosPral, secundarios: arrayTramosSec}};

    this.arrayLotecillos.push(lotecillo);

    return true;
  }

  deleteRowLotecillos(index: any) {  
    if(this.arrayLotecillos.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 
      return false;  
    } else {  
        this.arrayLotecillos.splice(index, 1);  
        return true;  
    }  
  }

  deleteRowLotecillos2(index: any) {  
    if(this.arrayLotecillos.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 
      return false;  
    } else {  
        this.arrayLotecillos.splice(index, 1);  
        return true;  
    }  
  }

  //----------------------- tramos

  addRowTramoLotecillo(indice: any) {     
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};  
    this.arrayLotecillos[indice].tramos.principales.push(nuevoTramo)
    return true;  
  }  

  deleteRowTramoLotecillo(indexLote: any, indexTramo: any) {  
    if(this.arrayLotecillos[indexLote].tramos.principales.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 

      var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
      this.arrayLotecillos[indexLote].tramos.principales.pop();
      this.arrayLotecillos[indexLote].tramos.principales.push(nuevoTramo);

      return false;  
    } else {  
      this.arrayLotecillos[indexLote].tramos.principales.splice(indexTramo, 1);  
      return true;  
    }  
  } 

  addRowTramoSecLotecillo(indice: any) {     
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};  
    this.arrayLotecillos[indice].tramos.secundarios.push(nuevoTramo)
    return true;  
  } 

  deleteRowTramoSecLotecillo(indexLote: any, indexTramo: any) {  
    if(this.arrayLotecillos[indexLote].tramos.secundarios.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 

      var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
      this.arrayLotecillos[indexLote].tramos.secundarios.pop();
      this.arrayLotecillos[indexLote].tramos.secundarios.push(nuevoTramo);

      return false;  
    } else {  
      this.arrayLotecillos[indexLote].tramos.secundarios.splice(indexTramo, 1);  
      return true;  
    }  
  } 

  submittedNumeroIdContrato = false;
  submittedGestion = false;
  submittedSector = false;
  submittedDescripcion = false;
  submittedOrigenSuministro = false;
  submittedFechaSuscripcion = false;
  submittedFechaFinalizacion = false;
  submittedMontoAdjudicado = false;

  submittedEmpresaAdjudicada = false;
  submittedLote = false;

  submittedJustificacion = false;
  validate(): boolean{
    var valid:boolean = true
    
    this.submittedNumeroIdContrato = false
    if ((typeof this.data.informacionGeneral.numeroContrato == 'undefined' || this.data.informacionGeneral.numeroContrato < 1)
        && this.data.informacionGeneral.idTemporal.trim()===''){
      this.showToast('dark', 'El contrato debe tener al menos un número de contrato o identificador temporal válido.')
      this.submittedNumeroIdContrato = true
      valid = false
    }     
    this.submittedGestion = false
    var gestion = +this.data.informacionGeneral.gestion!
    if (isNaN(gestion) || gestion<this.gestionActual){
      this.showToast('dark', 'La gestión no es válida.')
      this.submittedGestion = true
      valid = false
    }
    this.submittedSector = false
    if (this.data.informacionEspecifica.sector===''){
      this.showToast('dark', 'El sector no es válido.')
      this.submittedSector = true
      valid = false
    }    
    this.submittedDescripcion = false
    if (this.data.informacionEspecifica.descripcionContrato.trim()===''){
      this.showToast('dark', 'La descripción del contrato no es válida.')
      this.submittedDescripcion = true
      valid = false
    }
    this.submittedOrigenSuministro = false
    if (this.data.informacionEspecifica.medio===''){
      this.showToast('dark', 'El origen de suministro no es válido.')
      this.submittedOrigenSuministro = true
      valid = false
    }     
    this.submittedFechaSuscripcion = false
    if (this.data.informacionGeneral.fechaSuscripcion==''){
      this.showToast('dark', 'La fecha de suscripción del contrato no es válida.')
      this.submittedFechaSuscripcion = true
      valid = false
    }     
    this.submittedFechaFinalizacion = false
    if (this.data.vigencia.fechaFinalizacion==''){
      this.showToast('dark', 'La fecha de finalización del contrato no es válida.')
      this.submittedFechaFinalizacion = true
      valid = false
    }     
    this.submittedMontoAdjudicado = false
    var monto = +this.data.monto.montoAdjudicado!
    if (isNaN(monto) || monto<1){
      this.showToast('dark', 'El monto adjudicado no es válido.')
      this.submittedMontoAdjudicado = true
      valid = false
    } 

    this.submittedEmpresaAdjudicada = false
    if (typeof this.data.empresa.id == 'undefined' || this.data.empresa.id == 0){
      this.showToast('dark', 'Es necesario asociar a una empresa o a una asociación')
      this.submittedEmpresaAdjudicada = true
      valid = false
    }   
    this.submittedLote = false
    if (this.data.lotes.length==0){
      this.showToast('dark', 'Es necesario seleccionar al menos un lote.')
      this.submittedLote = true
      valid = false
    } 

    this.submittedJustificacion = false
    if (this.idContract>0 && this.justificacionCambio.trim()===''){
      this.showToast('dark', 'Es necesario resgistrar la justificación del cambio.')
      this.submittedJustificacion = true
      valid = false
    }

    return valid
  }

  submit(){
    if (this.validate()){
      this.updateTotales();

      if (this.isNew) this.save();
      else this.update();
    }
  }

  save(){
    var result:any;
    this.contratosService.guardarContratoSuministro(this.data).subscribe({
      next: (datum: any[]) => {
        console.log(datum);
        result = datum;
        if (result.success){
          this.showToast('success', 'La información del contrato se guardó satisfactoriamente.');
          this.idContract = result.data.id;
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información del contrato. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información del contrato. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })
  }

  update(){
    var result:any;
    console.log('contractid.....',this.idContract)
    this.contratosService.modificarContratoSuministro(this.idContract, this.data, this.justificacionCambio).subscribe({
      next: (datum: any[]) => {
        console.log(datum);
        result = datum;
        if (result.success){
          this.showToast('success', 'El contrato se guardó satisfactoriamente.');
          this.idContract = result.data.id;
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó el contrato. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó el contrato. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })
  }

  // for toaster messages
  /* typeMessage: ['success', 'danger', 'warning', 'info', 'primary', 'secondary', 'dark', 'light']; */
  showToast(typeMessage: any, messageText: any) {
    this.toaster.open({
      text: messageText,
      type: typeMessage,
      position: 'top-right'
    });
  }

  actualTipoEmpresa: any;
  onChangeEmpresa(valor:any){
    if (+valor>0)
      for (let index = 0; index < this.empresas.length; index++) {
        const element = this.empresas[index];
        if (element.id==valor) {
          this.actualTipoEmpresa = (element.esAsociacion==true)?'Asociación':'Empresa'
          break;
        }
      }
    else
      this.actualTipoEmpresa='';
  }

  updateTotales(){    
    // join all
    var productos: Array<Producto> = []
    this.data.informacionEspecifica.productos.forEach(element => {
      var newProducto = Object.assign({}, element)
      productos.push(newProducto)
    })
    this.data.adendas.forEach((adenda: Adenda)=>{
      adenda.productos.forEach((productoAdenda: Producto)=>{
        var newProducto = Object.assign({}, productoAdenda)
        productos.push(newProducto)
      })
    })    

    // reduce
    const aux = productos.reduce((acc:any, cur) => {
      const prod = cur.producto
      if (acc[prod]) acc[prod]+=cur.volumenAdjudicado
      else acc[prod]=cur.volumenAdjudicado
      return acc
    },{})

    // montus treatment
    
    const montoInicial = (this.data.monto.montoAdjudicado)?this.data.monto.montoAdjudicado:0
    const montus = montoInicial + this.data.adendas.reduce((acc, cur)=>acc+((cur.monto.montoAdjudicado)?cur.monto.montoAdjudicado:0),0)
    
    const montoEjecutado = (this.data.totalVolumenesMonto.monto.ejecutado)?this.data.totalVolumenesMonto.monto.ejecutado:0

    // building the official

    var volumenCtto: Array<VolumenContrato> = []
    Object.entries(aux).forEach(([key, value]) => {
      const nameProducto = this.parametricaProductos.find((p: any) => p.id === key).descripcion
      const volumenContrato: VolumenContrato = {
         producto: nameProducto,
         volumenNominado: (value as number)?value as number:0,
         volumenCargado: 0,    //provisional 
         volumenDescargado: 0  //provisional 
      }
      volumenCtto.push(volumenContrato)
    });
    
    this.data.totalVolumenesMonto = {
      volumen: volumenCtto,
      monto:{
        montoAdjudicado: montus,
        ejecutado: 0,  // provisional
        saldo: montus - montoEjecutado
      }
    }

  }

  // Para Certificaciones

  parametricaAllCertificaciones!: Array<Parametro>
  getAllCertificaciones(){
    this.certificacionesService.obtenerTodasCertificaciones()
    .subscribe(
      result => {
        if (result.success) {
          var todasCertificaciones = result.data.certificaciones
          
          this.parametricaAllCertificaciones = todasCertificaciones
            .filter((e:any)=>e.concepto==='CTTO')
            .map((a:any)=>({id: a.id, descripcion: 'No. '+a.numeroCertificacion+' ('+a.concepto+') - '+a.fechaEmision}))
        }        
      },
      error => {console.log('Retrieving problems!');}
    );      
  } 

  addRowCertificacion() {     
    var nuevaCertificacion = {id: 0, idProcesoContratacion: undefined, numero: undefined};  
    this.data.certificacionesPresupuestarias.push(nuevaCertificacion)
    return true;  
  } 

  deleteRowCertificacion(index: any) {  
    this.data.certificacionesPresupuestarias.splice(index, 1);
    return true;
  }

}
