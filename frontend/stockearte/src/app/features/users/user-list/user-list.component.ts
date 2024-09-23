import { Component } from '@angular/core';
import { Usuario } from '../../../shared/types/Usuario';
import { USUARIOS_MOCK } from '../../../shared/mock/usuario-mock';

@Component({
  selector: 'app-user-list',
  standalone: false,
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'apellido', 'username', 'rol', 'tienda', 'habilitado', 'edit', 'erase'];
  usuarios: Usuario[] = USUARIOS_MOCK;

  constructor() {
    
  }
}
