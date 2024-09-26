import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { Usuario, UsuariosArray } from '../../../shared/types/Usuario';
import { MatDialog } from '@angular/material/dialog';
import { UserFormComponent } from '../user-form/user-form.component';
import { UsersService } from '../../../core/services/users.service';
import { Notyf } from 'notyf';
import { ModalAction } from '../../../shared/types/ModalAction';
import { AuthService } from '../../../core/services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-list',
  standalone: false,
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnDestroy {
  displayedColumns: string[] = ['id', 'nombre', 'apellido', 'username', 'rol', 'tienda', 'habilitado', 'edit', 'erase'];
  dataSource: Usuario[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;
  private subscriptions: Subscription[] = [];

  constructor(
    private usersService: UsersService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadUsers();
  }

  ngOnDestroy(): void {
    // Cancelar todas las suscripciones al destruir el componente
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadUsers(): void {
    const sub = this.usersService.getUsers().subscribe({
      next: (users) => {
        this.dataSource = users.usuarios;
      },
      error: (err) => {
        this.notyf.error('Error al cargar usuarios');
        console.error(err);
      },
      complete: () => {
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(sub);
  }

  editUser(user: Usuario): void {
    const dialogRef = this.dialog.open(UserFormComponent, {
      width: '400px',
      data: { user: user, action: ModalAction.EDIT }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateUser(result);
      }
    });
    this.subscriptions.push(sub);
  }

  createUser(): void {
    const dialogRef = this.dialog.open(UserFormComponent, {
      width: '400px',
      data: { user: {} as Usuario, action: ModalAction.CREATE }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const createSub = this.usersService.createUser(result).subscribe({
          next: (response) => {
            response.message.includes('Error') ? this.notyf.error(response) : this.notyf.success(response);
            this.loadUsers();
          },
          error: (err) => {
            this.notyf.error('Error al crear usuario');
            console.error(err);
          },
        });
        this.subscriptions.push(createSub);
      }
    });
    this.subscriptions.push(sub);
  }

  updateUser(usuario: Usuario): void {
    const sub = this.usersService.modifyUser(usuario).subscribe({
      next: (response) => {
        response.message.includes('Error') ? this.notyf.error(response) : this.notyf.success(response);
      },
      error: (err) => {
        this.notyf.error('Error al actualizar usuario');
        console.error(err);
      },
      complete: () => {
        this.loadUsers();
      }
    });
    this.subscriptions.push(sub);
  }

  deleteUser(id: string): void {
    if (confirm('Eliminar no funcionará esta entrega')) {
      const sub = this.usersService.deleteUser(id).subscribe({
        next: () => {
          // this.notyf.success('Usuario eliminado con éxito');
          // this.loadUsers();
        },
        error: (err) => {
          this.notyf.error('Error al eliminar usuario');
          console.error(err);
        }
      });
      this.subscriptions.push(sub);
    }
  }
}
