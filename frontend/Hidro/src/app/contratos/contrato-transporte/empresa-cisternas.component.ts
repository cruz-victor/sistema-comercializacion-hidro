import { Component, OnInit } from '@angular/core';
import { FormBuilder } from "@angular/forms";

import { Toaster } from "ngx-toast-notifications";
import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../../ngb-date-i18n";

import { Empresa } from "../../contratos/interfaces-empresas"

import { ParametricasService } from "../../../app//parametricas.service";
import { EmpresasService } from "../../empresas.service";

@Component({
  selector: 'app-empresa-cisternas',
  templateUrl: './empresa-cisternas.component.html',
  styleUrls: ['./empresa-cisternas.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]  
})
export class EmpresaCisternasComponent implements OnInit {

  isNew: boolean = true;
  postId: any;

  empresa!: Empresa;
  asociaciones: any;

  nuevoParametro: any = {}; 

  constructor(private empresaService: EmpresasService,
              private toaster: Toaster,
              private parametricasService: ParametricasService,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.initEmpresa()

    this.getEmpresas()

    this.getPaises()

    this.getTiposConstitucionEmpresas()

  }

  initEmpresa(){
    this.empresa= {
      id: undefined,
      razonSocial: '',
      esAsociacion: false,
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
    this.empresa.fechaInicio = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }  

  buildDateFin(objDate:any) {
    this.empresa.fechaFin = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }  

  backBuildDateInicio(){
    if (this.empresa.fechaInicio){
      var date: Array<String>  = this.empresa.fechaInicio.split('-');
      this.fechaInicio = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }

  backBuildDateFin(){
    if (this.empresa.fechaFin){
      var date: Array<String>  = this.empresa.fechaFin.split('-');
      this.fechaFin = {year: +date[0], month: +date[1], day: +date[2]}
    } 
  }

  submittedRazonSocial: any;
  submittedPais: any;
  submittedTipoConstitucion: any;
  submittedFechaIni: any;
  submittedFechaFin: any;
  validate(): boolean{
    var valid:boolean = true
    // var antiquePermitted = 2
    
    this.submittedRazonSocial = false
    if (this.empresa.razonSocial.trim()===''){
      this.showToast('dark', 'La razón social de la empresa debe ser válida.')
      this.submittedRazonSocial = true
      valid = false
    }
    this.submittedPais = false
    if (this.empresa.pais.trim()===''){
      this.showToast('dark', 'El país de la empresa debe ser válido.')
      this.submittedPais = true
      valid = false
    }
    this.submittedTipoConstitucion = false
    if (this.empresa.tipoConstitucion==''){
      this.showToast('dark', 'El tipo de constitución debe ser válido.')
      this.submittedTipoConstitucion = true
      valid = false
    }    
    this.submittedFechaIni = false
    if (this.empresa.fechaInicio==''){
      this.showToast('dark', 'La fecha de inicio debe ser válida.')
      this.submittedFechaIni = true
      valid = false
    }      
    this.submittedFechaFin = false
    if (this.empresa.fechaFin==''){
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
    this.empresaService.guardarEmpresa(this.empresa).subscribe({
      next: (data: any[]) => {
        console.log(data);
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la empresa de transporte se guardó satisfactoriamente.');
          this.empresa.id = result.data.id;
          if (this.empresas) this.empresas.push(this.empresa);          
          this.postId = result.data.id;
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la empresa de transporte. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la empresa de transporte. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  update(){
    var result:any;
    this.empresaService.modificarEmpresa(this.postId, this.empresa).subscribe({
      next: (data: any[]) => {
        console.log(data);
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la empresa de transporte se guardó satisfactoriamente.');
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la empresa de transporte. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la empresa de transporte. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }  

  showToast(typeMessage: any, messageText: any) {
    this.toaster.open({
      text: messageText,
      type: typeMessage,
      position: 'top-right'
    });
  }

  // empresas
  empresaSeleccionada: any;
  empresas: any;
  getEmpresas(){
    this.empresaService.obtenerEmpresas()
     .subscribe(
       result => {
         var empresas = result
         if (empresas.success) {
          this.empresas = empresas.data.empresas.filter((element:any)=>!element.esAsociacion)
          .sort((a:any, b:any)=>{if (a.razonSocial<b.razonSocial) return -1; else return 1})
          console.log('lua   ',this.empresas)
         }
     },
     error => {console.log('Retrieving problems!');}
    ); 
  }  

  onChangeEmpresa(idEmpresa: any){
    this.isNew = false; 
    if (idEmpresa==0){
      this.initEmpresa();
      this.isNew = true;
    }
    else{
      this.empresaService.obtenerEmpresa(idEmpresa)
      .subscribe(
        result => {
          if (result.success) {
            this.empresa = result.data.empresa;
            console.log(this.empresa)
            this.postId = this.empresa.id;

            this.fechaInicio = {...this.dateAux}
            this.fechaFin = {...this.dateAux}

            this.backBuildDateInicio()
            this.backBuildDateFin()            
          }
      },
      error => {console.log('Retrieving problems!');}
      );  
    }
  
    }  

  parametricaPaises: any;
  getPaises(){
    this.parametricasService.obtenerParametrica('PAISES')
      .subscribe(
        result => {
          console.log(result)
          var datum: any = result; 
          if (datum.success) {
            this.parametricaPaises = datum.data.map((element: any)=>({id: element.codigo, descripcion: element.valor}))
            console.log(this.parametricaPaises)
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
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
}
