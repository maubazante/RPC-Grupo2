import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProveedorStockComponent } from './proveedor-stock.component';

describe('ProveedorStockComponent', () => {
  let component: ProveedorStockComponent;
  let fixture: ComponentFixture<ProveedorStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProveedorStockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProveedorStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
