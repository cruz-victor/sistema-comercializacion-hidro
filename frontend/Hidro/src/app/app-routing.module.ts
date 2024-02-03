import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from "./login/login.component";

import { ContratosComponent  } from "./contratos/contratos.component";
import { ContratoTransporteComponent } from "./contratos/contrato-transporte/contrato-transporte.component";
import { ContratoSuministroComponent } from "./contratos/contrato-suministro/contrato-suministro.component";
import { ContratoTransporteNacionalComponent } from "./contratos/contrato-transporte/contrato-transporte-nacional.component";
import { DashboardContratosComponent } from "./contratos/dashboard-contratos.component";

import { EmpresaCisternasComponent } from "./contratos/contrato-transporte/empresa-cisternas.component";
import { AsociacionComponent } from "./contratos/contrato-transporte/asociacion.component";
import { CisternasComponent } from "./contratos/contrato-transporte/cisternas.component";
import { LotesComponent } from "./contratos/contrato-transporte/lotes.component";
import { LotesNacionalesComponent } from "./contratos/contrato-transporte/lotes-nacionales.component";

import { EmpresaSuministroPlantaComponent } from "./contratos/contrato-suministro/empresa-suministro-planta.component";

import { PartidasComponent } from "./certificaciones-presupuestarias/partidas/partidas.component";
import { PartidaComponent } from "./certificaciones-presupuestarias/partidas/partida.component";
import { CertificacionesComponent } from './certificaciones-presupuestarias/certificaciones/certificaciones.component';
import { CertificacionComponent } from "./certificaciones-presupuestarias/certificaciones/certificacion.component";

import { DashboardCertificacionesComponent } from "./certificaciones-presupuestarias/certificaciones/dashboard-certificaciones.component";
import { DashboardPartidasComponent } from "./certificaciones-presupuestarias/partidas/dashboard-partidas.component";
import { CertificacionesPresupuestariasComponent } from "./certificaciones-presupuestarias/certificaciones-presupuestarias.component";

import { HomeComponent } from "./home/home.component";

import { AppComponent } from "./app.component";

import { ProgramacionComponent } from "./programacion/programacion.component";

const routes: Routes = [
  {
    path:'',
    component: LoginComponent
  }, 
  {
    path:'home',
    component: HomeComponent
  }, 
  {
    path:'app',
    component: AppComponent
  }, 
  {
    path:'contratos',
    component: ContratosComponent
  },  
  {
    path:'transporte',
    component: ContratoTransporteComponent
  },
  {
    path:'suministroPlantas',
    component: ContratoSuministroComponent
  },
  {
    path:'empresaSuministroPlanta',
    component: EmpresaSuministroPlantaComponent
  },
  {
    path:'empresaCisternas',
    component: EmpresaCisternasComponent
  },
  {
    path:'asociacion',
    component: AsociacionComponent
  },
  {
    path:'cisternas',
    component: CisternasComponent
  },
  {
    path:'lotes',
    component: LotesComponent
  },
  {
    path: 'lotesNacionales',
    component: LotesNacionalesComponent
  },
  {
    path: 'partidasPresupuestaria',
    component: PartidasComponent
  },
  {
    path: 'partidaPresupuestaria',
    component: PartidaComponent
  },
  {
    path: 'certificacionPresupuestaria',
    component: CertificacionComponent
  },
  {
    path: 'certificacionesPresupuestaria',
    component: CertificacionesComponent
  },
  {
    path: 'dashboardCertificaciones',
    component: DashboardCertificacionesComponent
  },
  {
    path: 'dashboardGeneralCertificaciones',
    component: CertificacionesPresupuestariasComponent
  },
  {
    path: 'dashboardPartidas',
    component: DashboardPartidasComponent
  },
  {
    path: 'transporteNacional',
    component: ContratoTransporteNacionalComponent
  },
  {
    path: 'dashboardContratos',
    component: DashboardContratosComponent
  },
  {
    path: 'programacion',
    component: ProgramacionComponent
  }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
