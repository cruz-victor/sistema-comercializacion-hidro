import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TransporteComponent } from './transporte/transporte.component';
import { PlantaComponent } from './planta/planta.component';

@NgModule({
  declarations: [
    AppComponent,
    TransporteComponent,
    PlantaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
