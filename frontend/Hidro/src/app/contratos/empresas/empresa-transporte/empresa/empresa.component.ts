import { Component, OnInit, Input} from '@angular/core';
import { FormControl, Validators, FormBuilder } from "@angular/forms";
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

// import { ParametricasService } from "../../../app//parametricas.service";
// import { ContratosService } from "../../../app/contratos.service";

import { HttpClient } from "@angular/common/http";

import { ViewChild} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {NgbAlert} from '@ng-bootstrap/ng-bootstrap';
import { analyzeAndValidateNgModules } from '@angular/compiler';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})
export class EmpresaComponent implements OnInit {

  data: any;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.data={
      id: Number,
      razonSocial: "",
      pais: "",
      esAsociacion: "",
      tipoDocumento: "",
      numeroDocumento: "",
      tipoConstitucion: "",
      email: "",
      direccion: "",
      sitioWeb: "",
      telefono: "",
      fechaRegistro: "",
      eliminado: "",
      usuarioAplicacion: "",
      justificacion: "",
      ipCliente: ""      
    };

  }

  formularioEmpresa = this.formBuilder.group({
    razonSocial: new FormControl('', [Validators.required]),
    pais: new FormControl('', [Validators.required, Validators.minLength(1)]),
    tipoDocumento: new FormControl(''),
    numeroDocumento: new FormControl(''),
    tipoConstitucion: new FormControl(''),
    email: new FormControl(''),
    direccion: new FormControl(''),
    sitioWeb: new FormControl(''),
    fechaRegistro: new FormControl(''),
    justificacion: new FormControl('')
  });


  submit(){

  }


}
