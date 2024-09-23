import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StoresRoutingModule } from './stores-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StoreListComponent } from './store-list/store-list.component';
import { MatDialogModule } from '@angular/material/dialog';
import { StoreFormComponent } from './store-form/store-form.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';


@NgModule({
  declarations: [StoreListComponent, StoreFormComponent],
  imports: [
    CommonModule,
    StoresRoutingModule,   
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
    MatSlideToggleModule
  ],
  exports: [StoreListComponent]
})
export class StoresModule { }
