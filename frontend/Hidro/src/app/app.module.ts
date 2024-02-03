import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContratosComponent } from './contratos/contratos.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SortableHeader } from './contratos/sortable.directive';

import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule } from "@angular/common/http";
import { LeftZeroPipe } from './left-zero.pipe';
import { ContratoComponent } from './contratos/contrato/contrato.component';
import { ContratoTransporteComponent } from './contratos/contrato-transporte/contrato-transporte.component';
import { ContratoSuministroComponent } from './contratos/contrato-suministro/contrato-suministro.component';
import { ContratoSuministroBuquesComponent } from './contratos/contrato-suministro-buques/contrato-suministro-buques.component';
import { EmpresaCisternasComponent } from './contratos/contrato-transporte/empresa-cisternas.component';
import { AsociacionComponent } from './contratos/contrato-transporte/asociacion.component';
import { CisternasComponent } from './contratos/contrato-transporte/cisternas.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastNotificationsModule } from "ngx-toast-notifications";
import { LotesComponent } from './contratos/contrato-transporte/lotes.component';
import { AdendaTransporteComponent } from './contratos/contrato-transporte/adenda-transporte.component';
import { EmpresaSuministroComponent } from './contratos/contrato-suministro-buques/empresa-suministro.component';
import { EmpresaSuministroPlantaComponent } from './contratos/contrato-suministro/empresa-suministro-planta.component';
import { CertificacionesPresupuestariasComponent } from './certificaciones-presupuestarias/certificaciones-presupuestarias.component';
import { PartidasComponent } from './certificaciones-presupuestarias/partidas/partidas.component';
import { CertificacionesComponent } from './certificaciones-presupuestarias/certificaciones/certificaciones.component';
import { DashboardCertificacionesComponent } from './certificaciones-presupuestarias/certificaciones/dashboard-certificaciones.component';
import { DashboardPartidasComponent } from './certificaciones-presupuestarias/partidas/dashboard-partidas.component';
import { ContratoTransporteNacionalComponent } from './contratos/contrato-transporte/contrato-transporte-nacional.component';
import { LotesNacionalesComponent } from './contratos/contrato-transporte/lotes-nacionales.component';
import { M3Pipe } from './m3.pipe';
import { ModificatorioComponent } from './contratos/contrato-suministro/modificatorio.component';
import { DashboardContratosComponent } from './contratos/dashboard-contratos.component';
import { CertificacionComponent } from './certificaciones-presupuestarias/certificaciones/certificacion.component';
import { PartidaComponent } from './certificaciones-presupuestarias/partidas/partida.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProgramacionComponent } from './programacion/programacion.component';

@NgModule({
  declarations: [
    AppComponent,
    ContratosComponent,
    SortableHeader,
    LeftZeroPipe,
    ContratoComponent,
    ContratoTransporteComponent,
    ContratoSuministroComponent,
    ContratoSuministroBuquesComponent,
    EmpresaCisternasComponent,
    AsociacionComponent,
    CisternasComponent,
    LotesComponent,
    AdendaTransporteComponent,
    EmpresaSuministroComponent,
    EmpresaSuministroPlantaComponent,
    CertificacionesPresupuestariasComponent,
    PartidasComponent,
    CertificacionesComponent,
    DashboardCertificacionesComponent,
    DashboardPartidasComponent,
    ContratoTransporteNacionalComponent,
    LotesNacionalesComponent,
    M3Pipe,
    ModificatorioComponent,
    DashboardContratosComponent,
    CertificacionComponent,
    PartidaComponent,
    LoginComponent,
    HomeComponent,
    ProgramacionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastNotificationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
