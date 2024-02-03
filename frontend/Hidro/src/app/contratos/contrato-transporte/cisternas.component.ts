import { Component, OnInit } from '@angular/core';

import { ContratosService } from "../../../app/contratos.service";
import { EmpresasService } from "../../empresas.service";

import { Toaster } from "ngx-toast-notifications";

interface Parametro {
  id: string;  
  descripcion: string;  
}

interface Camion {
  placa: String,
  marca: String,
  anio?: Number,
  color: String,
  capacidad?: Number,
  nroCompartimientos?: Number,
  nroChasis: String,
  disposicionEjes: String,
  estado: String,
  // unviewed
  id: Number,
  idEmpresa: Number,
  fechaInicio: any,
  fechaFin: any,
  justificacion: any,
  usuarioAplicacion: any
  ipCliente: any
}

interface CisternasEmpresa {
  cisternas: Array<Camion>;
}

@Component({
  selector: 'app-cisternas',
  templateUrl: './cisternas.component.html',
  styleUrls: ['./cisternas.component.css']
})
export class CisternasComponent implements OnInit {

  userApp: any = 'Txema'; //Revisar 

  camiones: Array<Camion> = [];

  nuevoCamion: any = {};


  idEmpresaAsociacion: any;

  cisternas:  CisternasEmpresa = {cisternas: this.camiones}

  //  = {
  //   cisternas: this.camiones
  // }

  empresas: any;  
  asociaciones: any;
  empresasAsociaciones: Array<Parametro> = []; 
  nuevoParametro: any = {}; 

  constructor(private contratosService: ContratosService,
              private toaster: Toaster,
              private empresasService: EmpresasService) { }

  // actualTipoEmpresa: any;
  // onChangeEmpresa(valor:any){
  //   if (+valor>0)
  //     for (let index = 0; index < this.empresas.length; index++) {
  //       const element = this.empresas[index];
  //       if (element.id==valor) {
  //         this.actualTipoEmpresa = (element.esAsociacion==true)?'Asociación':'Empresa'
  //         break;
  //       }
  //     }
  //   else
  //     this.actualTipoEmpresa='';
  // }
  
  
  ngOnInit(): void {

    //for join empresas and asociaciones
    this.empresasService.obtenerEmpresas()
    .subscribe(
      result => {
        this.empresas = result; 
        if (this.empresas.success) {
          this.empresas = this.empresas.data.empresas;
          this.empresasAsociaciones = this.empresas
            .map((element:any)=>({id: element.id, descripcion: element.razonSocial}))
            .sort((a:any, b:any)=> {if (a.descripcion<b.descripcion) return -11;  else return 1})

        }
    },
    error => {
      console.log('Retrieving problems!');
    }
    );

    // For camiones cisternas
    this.nuevoCamion = {idEmpresa: this.idEmpresaAsociacion, 
                        placa: '', marca: '', anio: null, color: '', capacidad: null, nroCompartimientos: null, nroChasis: '', disposicionEjes: '', estado: '',
                        id: 0, fechaInicio: null, fechaFin: null, justificacion: null, usuarioAplicacion: this.userApp, ipCliente: null
                        }
    this.camiones.push(this.nuevoCamion);

  } //------------ end Init

  isNew: boolean = true;
  postId: any;
  
  submit(){
    // if (this.isNew) this.save();
    // else this.update();

    this.save();
  }

  save(){
    var result:any;
    this.empresasService.guardarCisternas(this.cisternas).subscribe({
      next: (datum: any[]) => {
        console.log(datum);
        result = datum;
        if (result.success){
          this.showToast('success', 'La información de las cisternas se guardó satisfactoriamente.');
          this.postId = result.data.id;
          this.isNew = false;
          console.log('postId.........>'+this.postId);
          console.log('Contrato saved successfully!');
          
        }
      },
      error: (error: any) => {
        // result = error.message;
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de las cisternas. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de las cisternas. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');

        console.error('code errorrr------->: ', codError); //temporalisimi
        console.error('¡Errorr!: ', error); //temporalisimi
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

  update(){
    console.log('for updating....!')
  }



  dummy: any;
  onChange(){
    // var id = this.camiones[0].idEmpresa;  
    var id = this.idEmpresaAsociacion  
    if (Number(id)){
      this.empresasService.obtenerCisternasEmpresa(id.toString()) ////revisar
      .subscribe(
        result => {
          this.dummy = result; 
          if (this.dummy.success) {
            if (this.dummy.data.cisternas.length>0){
              console.log('entro aqy...por true')
              this.camiones = this.dummy.data.cisternas
              this.cisternas.cisternas = this.dummy.data.cisternas
              this.isNew = false;
            }
            else{
              console.log('entro aqy...por faaalse')
              this.camiones = [];
              this.cisternas = {cisternas: this.camiones};
              this.nuevoCamion = {idEmpresa: id, 
                                  placa: '', marca: '', anio: null, color: '', capacidad: null, nroCompartimientos: null, nroChasis: '', disposicionEjes: '', estado: '',
                                  id: 0, fechaInicio: null, fechaFin: null, justificacion: null, usuarioAplicacion: this.userApp, ipCliente: null
                                 }
              this.camiones.push(this.nuevoCamion);
              this.isNew = true;
            }
          }
      },
      error => {
        console.log('Retrieving problems!');
      }
      ); 
    }
    else{
      this.camiones = [];
      this.dummy = {};
    }
   
  }

  // treatment for camiones
  addRow() {    
    this.nuevoCamion = {idEmpresa: this.idEmpresaAsociacion, 
                        placa: '', marca: '', anio: null, color: '', capacidad: null, nroCompartimientos: null, nroChasis: '', disposicionEjes: '', estado: '',
                        id: 0, fechaInicio: null, fechaFin: null, justificacion: null, usuarioAplicacion: this.userApp, ipCliente: null
                      }
    this.camiones.push(this.nuevoCamion);  
    return true;  
  }  
  
  deleteRow(index: any) {  
    if(this.camiones.length == 1) {  
      console.log("Can't delete the row when there is only one truck, but..."); 
      this.nuevoCamion = {idEmpresa: 0, 
                          placa: '', marca: '', anio: null, color: '', capacidad: null, nroCompartimientos: null, nroChasis: '', disposicionEjes: '', estado: '',
                          id: 0, fechaInicio: null, fechaFin: null, justificacion: null, usuarioAplicacion: this.userApp, ipCliente: null
                         }
      this.camiones.pop();
      this.camiones.push(this.nuevoCamion);
      return false;  
    } else {  
        this.camiones.splice(index, 1);  
        return true;  
    }  
  }

  //For empresas
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

}
