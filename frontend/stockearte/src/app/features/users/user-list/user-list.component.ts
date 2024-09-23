import { ChangeDetectorRef, Component } from '@angular/core';
import { Usuario, UsuariosArray } from '../../../shared/types/Usuario';
import { MatDialog } from '@angular/material/dialog';
import { UserFormComponent } from '../user-form/user-form.component';
import { UsersService } from '../../../core/services/users.service';
import { Notyf } from 'notyf';

@Component({
  selector: 'app-user-list',
  standalone: false,
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'apellido', 'username', 'rol', 'tienda', 'habilitado', 'edit', 'erase'];
  dataSource: Usuario[] = []
  notyf = new Notyf({duration: 2000, position: {x: 'right', y: 'top',},});
  
  constructor(
    private usersService: UsersService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.usersService.getUsers().subscribe({
      next: (users) => {
        this.dataSource = users.usuarios
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.notyf.error('Error al cargar usuarios');
        console.error(err);
      }
    });
  }

  editUser(user: Usuario): void {
    const dialogRef = this.dialog.open(UserFormComponent, {
      width: '400px',
      data: { user: user }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateUser(result);
      }
    });
  }

  createUser(): void {
    const dialogRef = this.dialog.open(UserFormComponent, {
      width: '400px',
      data: { user: {} as Usuario }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.usersService.createUser(result).subscribe({
          next: (newUser) => {
            this.dataSource.push(newUser);  
            this.notyf.success('Usuario creado con éxito');
          },
          error: (err) => {
            this.notyf.error('Error al crear usuario');
            console.error(err);
          }
        });
      }
    });
  }

  updateUser(usuario: Usuario): void {
    console.log("USUARIO DTO", usuario)
    this.usersService.modifyUser(usuario).subscribe({
      next: (response) => {
        this.notyf.success(response);
        this.loadUsers();
      },
      error: (err) => {
        this.notyf.error('Error al actualizar usuario');
        console.error(err);
      }
    });
  }

  // Eliminar usuario no funciona
  deleteUser(id: string): void {
    if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
      this.usersService.deleteUser(id).subscribe({
        next: () => {
          this.notyf.success('Usuario eliminado con éxito');
          this.loadUsers();
        },
        error: (err) => {
          this.notyf.error('Error al eliminar usuario');
          console.error(err);
        }
      });
    }
  }
}