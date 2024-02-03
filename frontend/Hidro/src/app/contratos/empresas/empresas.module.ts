import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EmpresaTransporteComponent } from './empresa-transporte/empresa-transporte.component';
import { EmpresaSuministroComponent } from './empresa-suministro/empresa-suministro.component';
import { EmpresaComponent } from './empresa-transporte/empresa/empresa.component';


// import { BrowserModule } from '@angular/platform-browser';
// import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    EmpresaTransporteComponent,
    EmpresaSuministroComponent,
    EmpresaComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class EmpresasModule { }
