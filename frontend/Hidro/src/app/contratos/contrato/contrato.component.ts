import { Component, OnInit } from '@angular/core';

import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.css']
})
export class ContratoComponent implements OnInit {

  model!: NgbDateStruct;

  cadena: String;
  constructor() {
    this.cadena = 'init value of ....';
   }

  ngOnInit(): void {
  }



}
