import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialogModule } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { CatalogsFormComponent } from './catalogs-form/catalogs-form.component';
import { CatalogsListComponent } from './catalogs-list/catalogs-list.component';
import { CatalogsRoutingModule } from './catalogs-routing.module';



@NgModule({
  declarations: [CatalogsFormComponent, CatalogsListComponent],
  imports: [
    CommonModule,
    CatalogsRoutingModule,
    MatTableModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    MatTooltipModule,
    MatSlideToggleModule,
    MatIconModule,
    FormsModule
  ],
  exports: [CatalogsListComponent]
})
export class CatalogsModule { }