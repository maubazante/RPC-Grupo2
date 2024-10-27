import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { Usuario, UsuariosArray } from '../../../shared/types/Usuario';
import { MatDialog } from '@angular/material/dialog';
import { UserFormComponent } from '../user-form/user-form.component';
import { UsersService } from '../../../core/services/users.service';
import { Notyf } from 'notyf';
import { ModalAction } from '../../../shared/types/ModalAction';
import { AuthService } from '../../../core/services/auth.service';
import { debounceTime, distinctUntilChanged, Subject, Subscription } from 'rxjs';

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
  soloHabilitados: boolean = true;
  searchTerm$ = new Subject<string>();
  searchTerm: string = '';

  constructor(
    private usersService: UsersService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadUsers();

    this.subscriptions.push(
      this.searchTerm$.pipe(
        debounceTime(300),
        distinctUntilChanged()
      ).subscribe(searchTerm => {
        this.searchTerm = searchTerm;
        this.filterUsers(searchTerm);
      })
    );
  }

  ngOnDestroy(): void {
    // Cancelar todas las suscripciones al destruir el componente
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadUsers(): void {
    const sub = this.usersService.getUsers(this.soloHabilitados).subscribe({
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
      const sub = this.usersService.deleteUser(id).subscribe({
        next: () => {
          this.notyf.success('Usuario eliminado con éxito');
        },
        error: (err) => {
          this.notyf.error('Error al eliminar usuario');
          console.error(err);
        },
        complete: () => {
          this.loadUsers();
        }
      });
      this.subscriptions.push(sub);
  }

  filterUsers(searchTerm: string): void {
    this.dataSource = this.dataSource.filter(usuario =>
      usuario.nombre.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.searchTerm$.next('');
    this.loadUsers();
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
  }

  toggleHabilitadas(event: any): void {
    this.soloHabilitados = event.checked;
    this.loadUsers(); 
  }
}
