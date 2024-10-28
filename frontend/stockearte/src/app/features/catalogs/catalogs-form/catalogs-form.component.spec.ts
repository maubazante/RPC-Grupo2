import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogsFormComponent } from './catalogs-form.component';

describe('CatalogsFormComponent', () => {
  let component: CatalogsFormComponent;
  let fixture: ComponentFixture<CatalogsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogsFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
