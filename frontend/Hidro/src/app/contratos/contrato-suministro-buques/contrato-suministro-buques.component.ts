import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from "@angular/forms";
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

import {ViewChild} from '@angular/core';
import {NgbTypeahead} from '@ng-bootstrap/ng-bootstrap';
import {Observable, Subject, merge, OperatorFunction} from 'rxjs';
import {debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';

import { ParametricasService } from "../../../app//parametricas.service";

import { EmpresasService } from "../../empresas.service";
import { ContratosService } from "../../../app/contratos.service";

import { Toaster } from "ngx-toast-notifications";
import { element } from 'protractor';

const states = ['Acta de cierre de contrato', 'Ultima cisterna descargada', 'Ultima cisterna cargada'];
const years = [2020, 2021, 2022];

export interface ContratoSuministroBuques {
  informacionGeneral: { 
    numeroContrato?: Number
    idTemporal?: String
    fechaSuscripcion: String
    gestion: Number 
  },
  informacionEspecifica: {
    clase: String
    tipo: String
    ambito: String
    medio: String
    sector: String
    producto: String
    descripcionContrato: String
  },
  empresa: {
    id?: String
  },
  vigencia: {
    fechaFinalizacion: String
  },
  nominacionVolumen: {
    volumenNominado?: Number
    volumenEntregado?: Number
  },
  certificacionPresupuestaria: {
    id: Number
    idProcesoContratacion?: Number
    numero?: Number
  },
  informacionUsuario: {
    usuario: String
    ip: String
  }
};

interface Parametro {
  id: string;  
  descripcion: string;  
}

@Component({
  selector: 'app-contrato-suministro-buques',
  templateUrl: './contrato-suministro-buques.component.html',
  styleUrls: ['./contrato-suministro-buques.component.css']
})
export class ContratoSuministroBuquesComponent implements OnInit {

// Json data object principal: Contrato

contrato!: ContratoSuministroBuques;

  // local parametrics
  sectores: any;
  productos: any;
  criteriosFinalizacion: any;
  mediosSuministro: any;
  plotes: any;

  isNew: boolean = true;

  empresas: Array<Parametro> = []; 

constructor(private empresasService: EmpresasService, 
  private parametricasService: ParametricasService, 
  private contratosService: ContratosService,
  private toaster: Toaster) {}

// Tratamiento de fechas

fechaSuscripcion!: NgbDateStruct;
fechaFinalizacion!: NgbDateStruct;

leftZeroing(valor: number, times: number){
  var zeros: String = '';
  for (let i = 0; i < times; i++) zeros = zeros.concat('0');
  zeros = zeros.concat(valor.toString());
  return zeros.substr(zeros.length-times, zeros.length-1)
}

buildDateSuscripcion(objDate:any) {
  this.contrato.informacionGeneral.fechaSuscripcion = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
}

buildDateFinalizacion(objDate:any){
  this.contrato.vigencia.fechaFinalizacion = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
}


ngOnInit(): void {
  this.initContrato();

  // this.sectores = this.parametricasService.sectores();
  // this.productos = this.parametricasService.productos();
  // this.criteriosFinalizacion = this.parametricasService.criterioFinalizacion();
  // this.mediosSuministro = this.parametricasService.medioSuministro();
  // this.plotes = this.parametricasService.lotes();

  this.obtenerEmpresasSuministro()
}

initContrato(){
  this.contrato =  {
    informacionGeneral: { 
      numeroContrato: undefined,
      idTemporal: '',
      fechaSuscripcion: '',
      gestion: (new Date()).getFullYear()
    },
    informacionEspecifica: {
      clase: "SERV",
      tipo: "TRANS",
      ambito: "INTER",
      medio: "BUQUE",
      sector: '',
      producto: '',
      descripcionContrato: ''
    },
    empresa: {
      id: undefined
    },
    vigencia: {
      fechaFinalizacion: ''
    },
    nominacionVolumen: {
      volumenNominado: undefined,
      volumenEntregado: undefined
    },
    certificacionPresupuestaria: {
      id: 0,
      idProcesoContratacion: undefined,
      numero: undefined,
    },
    informacionUsuario: {
      usuario: '',
      ip: ''
    }  
  }
}

obtenerEmpresasSuministro(){
  this.empresasService.obtenerEmpresas()
   .subscribe(
     result => {
      // this.empresas = result; 
       if (result.success) {
         this.empresas = result.data.empresas.filter((element:any)=>element.tipoConstitucion==="EXT")
           .map((element:any)=>({id: element.id, descripcion: element.razonSocial}))

        // this.empresas = result.data.empresas;;
        // this.empresas =  this.empresas.filter((element:any)=>element.tipoConstitucion=="EXT")
          // .sort((a:any, b:any)=>{if (a.razonSocial<b.razonSocial) return -1; else return 1})
       }
   },
   error => {
     console.log('Retrieving problems!');
   }
   ); 
}

trash: any;
submit(){
  console.log("submitted");

}

}
