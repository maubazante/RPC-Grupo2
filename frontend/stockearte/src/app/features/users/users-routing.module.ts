import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { AuthGuard } from '../../core/guards/app.guard';
import { Rol } from '../../shared/types/Rol';

const routes: Routes = [
  { path: '', component: UserListComponent, canActivate: [AuthGuard], data: { privilege: [Rol.ADMIN] } }  // Ruta principal que carga la lista de usuarios
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {}
