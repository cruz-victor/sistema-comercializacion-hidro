import { Component, OnInit } from '@angular/core';
import { FormBuilder } from "@angular/forms";

import { EmpresasService } from "../../empresas.service";
import { ParametricasService } from "../../../app//parametricas.service";

import { Toaster } from "ngx-toast-notifications";
import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../../ngb-date-i18n";

import { Empresa } from "../../contratos/interfaces-empresas"

@Component({
  selector: 'app-asociacion',
  templateUrl: './asociacion.component.html',
  styleUrls: ['./asociacion.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]
})
export class AsociacionComponent implements OnInit {
 
  isNew: boolean = true;
  postId: any;

  asociacion!: Empresa;

  constructor(private empresaService: EmpresasService,
              private toaster: Toaster,
              private parametricasService: ParametricasService) { }

  ngOnInit(): void {
   this.initAsociacion()

    this.getAsociaciones();

    this.getTiposConstitucionEmpresas()
  }

  initAsociacion(){
    this.asociacion= {
      id: undefined,
      razonSocial: '',
      esAsociacion: true,
      pais: '',
      tipoDocumento: '',
      numeroDocumento: '',
      tipoConstitucion: '',
      email: '',
      direccion: '',
      sitioWeb: '',
      telefono: '',
      representanteLegal: '',
      fechaInicio: '',
      fechaFin: '',
      usuarioAplicacion: '',
      justificacion: '',
      ipCliente: '',
      hash: '',
    }

    this.fechaInicio = {...this.dateAux}
    this.fechaFin = {...this.dateAux}
  }


  submittedRazonSocial: any;
  submittedTipoConstitucion: any;
  submittedFechaIni: any;
  submittedFechaFin: any;
  validate(): boolean{
    var valid:boolean = true
    // var antiquePermitted = 2
    
    this.submittedRazonSocial = false
    if (this.asociacion.razonSocial.trim()===''){
      this.showToast('dark', 'El nombre de la asociación debe ser válida.')
      this.submittedRazonSocial = true
      valid = false
    }
    this.submittedTipoConstitucion = false
    if (this.asociacion.tipoConstitucion==''){
      this.showToast('dark', 'El tipo de constitución debe ser válido.')
      this.submittedTipoConstitucion = true
      valid = false
    }    
    this.submittedFechaIni = false
    if (this.asociacion.fechaInicio==''){
      this.showToast('dark', 'La fecha de inicio debe ser válida.')
      this.submittedFechaIni = true
      valid = false
    }      
    this.submittedFechaFin = false
    if (this.asociacion.fechaFin==''){
      this.showToast('dark', 'La fecha de finalización debe ser válida.')
      this.submittedFechaFin = true
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
    this.empresaService.guardarAsociacion(this.asociacion).subscribe({
      next: (data: any) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la asociación se guardó satisfactoriamente.');
          this.asociacion.id = result.data.id;

          //if (this.empresas) this.empresas.push(this.empresa);          
          this.postId = result.data.id;
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la asociación. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la asociación. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  update(){
    var result:any;
    this.empresaService.modificarAsociacion(this.asociacion, this.postId).subscribe({
      next: (data: any[]) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la empresa de suministro se guardó satisfactoriamente.');
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la empresa de suministro. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la empresa de suministro. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

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

  // for dating
  fechaInicio!: NgbDateStruct;
  fechaFin!: NgbDateStruct;
  dateAux!: NgbDateStruct;

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }

  buildDateInicio(objDate:any) {
    this.asociacion.fechaInicio = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }  

  buildDateFin(objDate:any) {
    this.asociacion.fechaFin = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }  

  backBuildDateConstitucion(){
    if (this.asociacion.fechaInicio){
      var date: Array<String>  = this.asociacion.fechaInicio.split('-');
      this.fechaInicio = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }

  backBuildDateFinVigencia(){
    if (this.asociacion.fechaFin){
      var date: Array<String>  = this.asociacion.fechaFin.split('-');
      this.fechaFin = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }


  // asociaciones
  asociacionSeleccionada: any;
  asociaciones: any;
  getAsociaciones(){
    this.empresaService.obtenerAsociaciones()
     .subscribe(
       result => {
         var asociaciones = result; 
         if (asociaciones.success) {
           this.asociaciones = asociaciones.data.empresas.filter((element:any)=>element.esAsociacion)
                                .sort((a:any, b:any)=>{if (a.razonSocial<b.razonSocial) return -1; else return 1})
         }
     },
     error => {console.log('Retrieving problems!');}
    ); 
  }

  //empresa = {};
  onChangeAsociacion(idAsociacion: any){
    this.isNew = false; 
    if (idAsociacion==0){
      this.initAsociacion();
      this.isNew = true;
    }
    else{
      this.empresaService.obtenerAsociacion(idAsociacion)
      .subscribe(
        result => {
          if (result.success) {
            this.asociacion = result.data.empresa;
            this.postId = this.asociacion.id;
            
            this.fechaInicio = {...this.dateAux}
            this.fechaFin = {...this.dateAux}

            this.backBuildDateConstitucion()
            this.backBuildDateFinVigencia()
          }
      },
      error => {console.log('Retrieving problems!');}
      ); 
    }
  
  }
 
  parametricaTiposConstitucionEmpresas: any;
  getTiposConstitucionEmpresas(){
    this.parametricasService.obtenerParametrica('TIPO_CONSTITUCION_EMPRESAS')
      .subscribe(
        result => {
          console.log(result)
          var datum: any = result; 
          if (datum.success) {
            this.parametricaTiposConstitucionEmpresas = datum.data.map((element: any)=>({id: element.codigo, descripcion: element.valor}))
            console.log(this.parametricaTiposConstitucionEmpresas)
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
  }  

  // trash

  world = new Date();
  worldcito = this.world.getUTCDate;

}
