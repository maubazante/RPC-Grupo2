import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StoresRoutingModule } from './stores-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { StoreListComponent } from './store-list/store-list.component';


@NgModule({
  declarations: [StoreListComponent],
  imports: [
    CommonModule,
    StoresRoutingModule,   
    MatTableModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
  ],
  exports: [StoreListComponent]
})
export class StoresModule { }
