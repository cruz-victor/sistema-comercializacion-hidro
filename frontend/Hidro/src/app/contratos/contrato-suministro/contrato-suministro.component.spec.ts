import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContratoSuministroComponent } from './contrato-suministro.component';

describe('ContratoSuministroComponent', () => {
  let component: ContratoSuministroComponent;
  let fixture: ComponentFixture<ContratoSuministroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContratoSuministroComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratoSuministroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
