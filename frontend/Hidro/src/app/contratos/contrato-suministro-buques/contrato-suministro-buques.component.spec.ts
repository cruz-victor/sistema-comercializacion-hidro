import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContratoSuministroBuquesComponent } from './contrato-suministro-buques.component';

describe('ContratoSuministroBuquesComponent', () => {
  let component: ContratoSuministroBuquesComponent;
  let fixture: ComponentFixture<ContratoSuministroBuquesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContratoSuministroBuquesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratoSuministroBuquesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
