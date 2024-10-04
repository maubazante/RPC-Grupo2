import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { OrdenesComponent } from './features/ordenes/ordenes.component';
import { NovedadesComponent } from './features/novedades/novedades.component';
import { ProveedorStockComponent } from './features/proveedor-stock/proveedor-stock.component';

export const routes: Routes = [
  { path: 'ordenes', component: OrdenesComponent }, 
  { path: 'novedades', component: NovedadesComponent },  
  { path: 'proveedor-stock', component: ProveedorStockComponent },  
  { path: '', redirectTo: '/ordenes', pathMatch: 'full' }, 
  { path: '**', redirectTo: '/ordenes' }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],  
  exports: [RouterModule]
})
export class AppRoutingModule { }
