import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AuthGuard } from "../../core/guards/app.guard";
import { Rol } from "../../shared/types/Rol";
import { OrderListComponent } from "./order-list/order-list.component";

const routes: Routes = [
    { path: '', component: OrderListComponent, canActivate: [AuthGuard], data: { privilege: [Rol.ADMIN, Rol.STOREMANAGER] } }  // Ruta principal para listar los productos
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class OrdersRoutingModule {}
  