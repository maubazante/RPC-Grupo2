import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../../core/guards/app.guard';
import { Rol } from '../../shared/types/Rol';
import { ReportsListComponent } from './reports-list/reports-list.component';

const routes: Routes = [
  { path: '', component: ReportsListComponent, canActivate: [AuthGuard], data: { privilege: [Rol.ADMIN, Rol.STOREMANAGER] } }  // Ruta principal para listar los productos
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule {}
