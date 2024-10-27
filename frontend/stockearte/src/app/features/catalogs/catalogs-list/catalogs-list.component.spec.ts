import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogsListComponent } from './catalogs-list.component';

describe('CatalogsListComponent', () => {
  let component: CatalogsListComponent;
  let fixture: ComponentFixture<CatalogsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
