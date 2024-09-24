import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StoreListComponent } from './store-list/store-list.component';
import { AuthGuard } from '../../core/guards/app.guard';

const routes: Routes = [
  { path: '', component: StoreListComponent, canActivate: [AuthGuard] }  // Ruta principal para listar las tiendas
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StoresRoutingModule {}
