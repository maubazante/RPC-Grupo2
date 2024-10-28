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
import { ReportsFormComponent } from './reports-form/reports-form.component';
import { ReportsListComponent } from './reports-list/reports-list.component';
import { ReportsRoutingModule } from './reports-routing.module';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';


@NgModule({
  declarations: [ReportsFormComponent, ReportsListComponent],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    MatTableModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatDatepickerModule,
    MatDialogModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    MatTooltipModule,
    MatSlideToggleModule,
    MatIconModule,
    FormsModule
  ],
  exports: [ReportsListComponent]
})
export class ReportsModule { }