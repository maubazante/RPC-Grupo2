import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product-list/product-list.component';
import { AuthGuard } from '../../core/guards/app.guard';

const routes: Routes = [
  { path: '', component: ProductListComponent, canActivate: [AuthGuard] }  // Ruta principal para listar los productos
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule {}
