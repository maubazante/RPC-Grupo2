import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AuthGuard } from "../../core/guards/app.guard";
import { Rol } from "../../shared/types/Rol";
import { NewsListComponent } from "./news-list/news-list.component";

const routes: Routes = [
    { path: '', component: NewsListComponent, canActivate: [AuthGuard], data: { privilege: [Rol.ADMIN] } }  // Ruta principal para listar los productos
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
  })
  export class NewsRoutingModule {}
  