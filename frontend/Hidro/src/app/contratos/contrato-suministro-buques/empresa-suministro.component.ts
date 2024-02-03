import { Component, OnInit } from '@angular/core';

import { EmpresasService } from "../../empresas.service";

import { Toaster } from "ngx-toast-notifications";

interface Parametro {
  id: string; 
  descripcion: string;  
}

interface Empresa{
  id?: Number,
  razonSocial: String,
  pais: String,
  esAsociacion: Boolean,
  tipoDocumento: String,
  numeroDocumento: String,
  tipoConstitucion: String,
  email: String,
  direccion: String,
  sitioWeb: String,
  telefono: String,
  representanteLegal: String,
  fechaConstitucionAsociacion: String,
  fechaFinVigencia: String,
  idAsociacion?: Number,
  usuarioAplicacion: String,
  justificacion: String,
  ipCliente: String, 
  hash: String
}

@Component({
  selector: 'app-empresa-suministro',
  templateUrl: './empresa-suministro.component.html',
  styleUrls: ['./empresa-suministro.component.css']
})
export class EmpresaSuministroComponent implements OnInit {

  isNew: boolean = true;
  postId: any;

  empresa: any;
  asociaciones: any;

  empresasAsociaciones: Array<Parametro> = []; 
  nuevoParametro: any = {}; 

  constructor(private empresaService: EmpresasService,
              private toaster: Toaster) { }

  ngOnInit(): void {

    this.initEmpresa();

    this.getEmpresas();

    // this.getAsociaciones();

  }

  initEmpresa(){
    this.empresa={
      id: undefined,   razonSocial: '',      pais: '',      esAsociacion: false,      tipoDocumento: "NIT",      numeroDocumento: '',
      tipoConstitucion: "EXT_BUQUE",            email: '',     direccion: '',            sitioWeb: '',              telefono: '',
      representanteLegal: '',             fechaConstitucionAsociacion:'',           idAsociacion: undefined,     usuarioAplicacion: '',
      justificacion: '',                  ipCliente: '',                            fechaFinVigencia: '',   hash: ''
    };    
  }

  // getAsociaciones(){
  //   var result: any;
  //   this.empresaService.obtenerEmpresas().subscribe({
  //     next: (data: any[]) => {
  //       console.log(data);
  //       this.asociaciones = data;
  //       if (this.asociaciones.success){
  //         this.asociaciones = this.asociaciones.data.empresas;
  //         this.empresasAsociaciones = this.asociaciones
  //           .filter((element:any)=>element.tipoConstitucionesAsociacion)
  //           .sort((a:any, b:any)=>{if (a.razonSocial<b.razonSocial) return -1; else return 1})
  //           .map((asoc:any)=>({id: asoc.id, descripcion: asoc.razonSocial}))
  //       }
  //     },
  //     error: error => {
  //       result = error.message;
  //       console.error('¡Errorr!: ', error);       
  //       // this.showToast('warning', 'No se guardó satisfactoriamente el contrato.') 
  //     }
  //   })
  // }

  submit(){
    if (this.isNew) this.save();
    else this.update();
  }

  save(){
    var result:any;
    this.empresaService.guardarEmpresa(this.empresa).subscribe({
      next: (data: any[]) => {
        console.log(data);
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la empresa de suministro se guardó satisfactoriamente.');
          this.empresa.id = result.data.id;
          if (this.empresas) this.empresas.push(this.empresa);          
          this.postId = result.data.id;
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

  update(){
    var result:any;
    this.empresaService.modificarEmpresa(this.postId, this.empresa).subscribe({
      next: (data: any[]) => {
        console.log(data);
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
        this.empresas = result; 
         if (result.success) {
          this.empresas = result.data.empresas;
          this.empresas =  this.empresas.filter((element:any)=>element.tipoConstitucion=="EXT_BUQUE")
            // .sort((a:any, b:any)=>{if (a.razonSocial<b.razonSocial) return -1; else return 1})
         }
     },
     error => {
       console.log('Retrieving problems!');
     }
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
            this.postId = this.empresa.id;
          }
      },
      error => {
        console.log('Retrieving problems!');
      }
      );  
    }
  
    }  
}
