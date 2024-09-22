import { Component } from '@angular/core';
import { Usuario } from '../../../shared/types/Usuario';
import { USUARIOS_MOCK } from '../../../shared/mock/usuario-mock';
import { MatDialog } from '@angular/material/dialog';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-user-list',
  standalone: false,
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'apellido', 'username', 'rol', 'tienda', 'habilitado', 'edit', 'erase'];
  usuarios: Usuario[] = USUARIOS_MOCK;

  constructor(private dialog: MatDialog) {
    
  }

  edit(usuario: any) {
    this.dialog.open(UserFormComponent, {
      width: '50vw',
      data:  {
        user: usuario,
        tiendas: []
      }
    })
  }

  delete(usuario: any) {
    // Todo delete, en otro momento se hará modal de ¿Estás seguro?
  }
}