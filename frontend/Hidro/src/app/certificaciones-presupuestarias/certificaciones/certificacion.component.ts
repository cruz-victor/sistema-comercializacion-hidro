import { Component, OnInit, Input} from '@angular/core';

import { Toaster } from "ngx-toast-notifications";

import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../../ngb-date-i18n";

import { ParametricasService } from "../../parametricas.service";
import { EmpresasService } from "../../empresas.service";
import { CertificacionesService } from "../../certificaciones.service";
import { ContratosService } from "./../../contratos.service";

import { CertificacionPresupuestaria, Parametro } from "../interfaces-presupuesto";

@Component({
  selector: 'app-certificacion',
  templateUrl: './certificacion.component.html',
  styleUrls: ['./certificacion.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]  
})
export class CertificacionComponent implements OnInit {

  @Input() idCertificacion!: any;

  isNew: boolean = true;

  certificacion!: CertificacionPresupuestaria

  gestionActual!: number

  justificacionCambio: string = ''

  constructor(private toaster: Toaster,
              private parametricasService: ParametricasService,
              private certificacionesService: CertificacionesService,
              private contratosService: ContratosService,
              private empresasService: EmpresasService) { 
    this.gestionActual = 2021

    this.initCertificacion()

    this.getConceptos()
    this.getTiposServicio()

    this.getLotesTransporteInternacional()
    this.getPartidas()

    this.getContratos()  ////temporalllllllllllllll
    this.getProcesosContratacion() ////tooo
  }

  ngOnInit(): void {


    if (+this.idCertificacion > 0){
      console.log(this.idCertificacion)

      this.isNew = false
      this.certificacionesService.obtenerCertificacion(+this.idCertificacion)
        .subscribe(
          (result: any) => {
            var datum = result; 
            if (datum.success && datum.data ) {
              this.certificacion = datum.data.certificacion;

              this.backBuildDateEmision();
              this.backLoadLotesTransporte();
            }
        },
        error => {
          console.log('Retrieving problems!');
        }
      );
    }
  }

  initCertificacion(){
    this.certificacion={
      id: undefined,
      concepto: '',
      idPartida: undefined,
      fechaEmision: '',
      numeroCertificacion: '',
      descripcion: '',
      montoMaximo: undefined,
      montoEjecutado: 0,
      saldo: undefined,
      tipoServicio: '',
      idProcesoContratacion: undefined,
      idContrato: undefined,
      idLotes: [],
      rutaDocumentoAdjunto: '',
      usuarioAplicacion: '',
      justificacion: '',
      ipCliente: '',
      hash: '',
      idCertificacion: 0
    }

    // search a better site for this code
    if (this.lotesTransporte) this.lotesTransporte.forEach((element:any) => element.checked = false);
  }

  cleanCertificacionReduced(){
    this.certificacion.tipoServicio = ''
    this.certificacion.idProcesoContratacion = undefined
    this.certificacion.idContrato = undefined
    this.certificacion.idContrato = undefined
    this.certificacion.idPartida = undefined
    this.certificacion.idLotes = [];
  }

  submittedGestion = false
  submittedCategoria = false
  submittedNumeroPartida = false
  submittedJustificacion = false
  validate(): boolean{
    var valid:boolean = true
    // var antiquePermitted = 2
    
    // this.submittedGestion = false
    // if (this.partida.gestion==null || this.partida.gestion<=(new Date()).getFullYear()-antiquePermitted){
    //   this.showToast('dark', 'La gestión debe ser válida.')
    //   this.submittedGestion = true
    //   valid = false
    // }
    // this.submittedCategoria = false
    // if (this.partida.categoria.trim()=='' || this.partida.categoria=='0'){
    //   this.showToast('dark', 'Una categoría debe ser seleccionada.')
    //   this.submittedCategoria = true
    // }    
    // this.submittedNumeroPartida = false;
    // if (this.partida.numeroPartida.trim()==''){
    //   this.showToast('dark', 'El numero de partida debe contener un valor válido.')
    //   this.submittedNumeroPartida = true
    //   valid = false
    // }    

    this.submittedJustificacion = false
    if (this.idCertificacion>0 && this.justificacionCambio.trim()===''){
      this.showToast('dark', 'Es necesario resgistrar la justificación del cambio.')
      this.submittedJustificacion = true
      valid = false
    }

    return valid
  }

 submit(){
    if (this.validate()){
      if (this.isNew) this.save();
      else this.update();
    }
  }

  save(){
    var result:any;
    this.certificacionesService.guardarCertificacion(this.certificacion).subscribe({
      next: (data: any[]) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la certificación presupuestaria se guardó satisfactoriamente.');
          this.certificacion.id = result.data.id;

          this.idCertificacion = result.data.id;

          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la certificación presupuestaria. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la certificación presupuestaria. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  update(){
    var result:any;
    this.certificacionesService.modificarCertificacion(this.idCertificacion, this.certificacion).subscribe({
      next: (data: any[]) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la certificación presupuestaria se guardó satisfactoriamente.');

          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la certificación presupuestaria. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la certificación presupuestaria. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  // for partidas

  partidas: any;
  parametricaPartidas!: Array<Parametro>
  getPartidas(){
    this.certificacionesService.obtenerPartidas()
    .subscribe(
      result => {
        if (result.success) {
          this.partidas = result.data.partidas;
          console.log(this.partidas)
          this.parametricaPartidas = this.partidas
            .map((element:any)=>({id: element.id, descripcion: element.numeroPartida+' ('+element.categoria+')'}))
        }        
      },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }  

  // for get certificaciones por concepto

  parametricaCertificaciones!: Array<Parametro>
  getCertificaciones(){

    this.certificacionesService.obtenerCertificaciones('CTTO')
    .subscribe(
      result => {
        if (result.success) {
          var cttos = result.data.certificaciones
          this.parametricaCertificaciones = cttos.map((element:any)=>({id: element.numeroCertificacion, descripcion: element.numeroCertificacion+' ('+element.concepto+')'}))

          this.certificacionesService.obtenerCertificaciones('ADENDA')
          .subscribe(
            outcome => {
              if (outcome.success) {
                var adendas = outcome.data.certificaciones
                adendas.forEach((ele: any) => {
                  this.parametricaCertificaciones.push({id: ele.numeroCertificacion, descripcion: ele.numeroCertificacion+' ('+ele.concepto+')'})
                });
              }        
            },
          error => {
            console.log('Retrieving problems!');
          }
          );  
          
        }        
      },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }

  // Para contratos

  contratos: any;
  parametricaContratos!: Array<Parametro>
  getContratos(){
    this.contratosService.obtenerContratos(this.gestionActual)
    .subscribe(
      result => {
        // console.log(result)
        this.contratos = result; 
        if (this.contratos.success) {
          this.contratos = this.contratos.data;
          this.parametricaContratos = this.contratos.map((x:any)=>({id: x.id, descripcion: x.contrato.informacionGeneral.numeroContrato}))
          // console.log(this.contratos)
          // console.log(this.parametricaContratos)
        }
    },
    error => {console.log('Retrieving problems!'); }
    );
  
  
  }

  // Para procesos de contratacion

  parametricaProcesosContratacion!: Array<Parametro>
  getProcesosContratacion(){
    this.certificacionesService.obtenerProcesosContratacion()
    .subscribe(
      result => {
        if (result.success) {
          var procesos = result.data.procesos
          this.parametricaProcesosContratacion = procesos.map((e:any)=>({id:e.id, descripcion:e.objectoContratacion}))
          console.log(this.parametricaProcesosContratacion)
          // adendas.forEach((ele: any) => {
          //   this.parametricaCertificaciones.push({id: ele.numeroCertificacion, descripcion: ele.numeroCertificacion+' ('+ele.concepto+')'})
          // });
        }        
      },
    error => {
      console.log('Retrieving problems!');
    }
    );  
  
  
  }


    // for dating

  fechaEmision!: NgbDateStruct;
  dateAux!: NgbDateStruct;

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }

  buildDateEmision(objDate:any) {
    this.certificacion.fechaEmision = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }  
  
  backBuildDateEmision(){
    if (this.certificacion.fechaEmision){
      var date: Array<String>  = this.certificacion.fechaEmision.split('-');
      console.log(date)
      this.fechaEmision = {year: +date[0], month: +date[1], day: +date[2]}
    } 
    else this.fechaEmision={...this.dateAux}
  }

  // Lotes
  lotesTransporte: any;
  getLotesTransporteInternacional(){
    this.empresasService.obtenerLotesTransporteInternacional(this.gestionActual)
    .subscribe(
      result => {
        if (result.success && result.data) {
          this.lotesTransporte = result.data.lotes
            .sort((a:any, b:any)=>{if (a.lote<b.lote) return -1; return 1})
            .map((lot:any)=>({id:lot.id, lote:lot.lote, checked:false}))
        }        
      },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }

  backLoadLotesTransporte(){
    // this.lotesTransporte.forEach((element:any) => element.checked = false);
    this.lotesTransporte.forEach((element:any) => {if (element>0) element.checked = false})

    if (this.certificacion.idLotes.length>0){
      this.lotesTransporte.forEach((elementLote:any) => {
        this.certificacion.idLotes.forEach((elementRec) => {
          if (elementLote.id===elementRec) elementLote.checked=true;          
        });
      });
    }
  }

  onChangeLote(valor:any, event:any){
    if (event.target.checked) this.certificacion.idLotes.push(+valor);
    else this.certificacion.idLotes = this.certificacion.idLotes.filter((element:any)=>element!=valor)
           
    console.log( this.certificacion.idLotes)
  }

  // Concepto

  isReduced: boolean = false;
  isAdenda: boolean = false;

  onChangeConcepto(valor: any){
    if (valor==='ADENDA'){
      this.certificacion.idProcesoContratacion = undefined
      this.isAdenda=true;
    } 
    else{
      this.certificacion.idContrato = undefined
      this.isAdenda=false;
    }
    
    if (valor==='CTTO' || valor==='ADENDA'){
      this.isReduced = false
    }
    else{
      this.getCertificaciones();
      this.cleanCertificacionReduced();
      this.isReduced = true
    }
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

  // para monto

  onChangeMonto(){
    this.certificacion.saldo = this.certificacion.montoMaximo! - this.certificacion.montoEjecutado!;
    // console.log('jjjjj')
  }  

  // Parametricas

  parametricaConceptos!: Array<Parametro>
  getConceptos(){
    this.parametricasService.obtenerParametrica('CERTIFICACION_CONCEPTOS')
      .subscribe(
        result => {
          var conceptos:any = result; 
          if (conceptos.success && conceptos.data) {
            this.parametricaConceptos = conceptos.data.map((element:any)=>({id:element.codigo, descripcion:element.valor}));
            console.log('conceptos.... ',this.parametricaConceptos)
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
  }

  parametricaTipoServicio!: Array<Parametro>
  getTiposServicio(){
    this.parametricasService.obtenerParametrica('CERTIFICACION_SERVICIOS')
      .subscribe(
        result => {
          var tipos:any = result; 
          if (tipos.success && tipos.data) {
            this.parametricaTipoServicio = tipos.data.map((element:any)=>({id:element.codigo, descripcion:element.valor}));
            console.log('tipos.... ',this.parametricaTipoServicio)
          }
      },
      error => {console.log('Retrieving problems!');}
    );      
  }

}


