import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { Rol } from '../../../shared/types/Rol';
import { Tienda } from '../../../shared/types/Tienda';
import { Usuario } from '../../../shared/types/Usuario';
import { ModalAction } from '../../../shared/types/ModalAction';
import { AuthService } from '../../../core/services/auth.service';
import { StoresService } from '../../../core/services/stores.service';
import { INotyfNotificationOptions, Notyf } from 'notyf';
import { UsersService } from '../../../core/services/users.service';
import { ModalCargaComponent } from '../modal-carga/modal-carga.component';
import { LoadingModalService } from '../../../core/services/loading-modal.service';

@Component({
  selector: 'app-user-form',
  standalone: false,
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent implements OnInit {
  userForm!: FormGroup;
  roles = Object.values(Rol);
  tiendas: Tienda[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top', }, });
  hide = true;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private userService: UsersService,
    private tiendaService: StoresService,
    public dialog: MatDialog,
    private loadingModal: LoadingModalService,
    public dialogRef: MatDialogRef<UserFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { user: Usuario, tiendas: Tienda[], action: string }
  ) {
    this.tiendas = data.tiendas || []
    this.isEdit = data.action === ModalAction.EDIT;
    this.loadForm();
  }

  ngOnInit() {
    this.loadTiendas();
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.userForm.valid) {
      const updatedUser = this.userForm.value;
      this.dialogRef.close(updatedUser);  // Devolver los datos actualizados al cerrar el dialog
    }
  }

  loadForm(): void {
    this.userForm = this.fb.group({
      id: [this.data.user.id],
      nombre: [this.data.user.nombre, Validators.required],
      apellido: [this.data.user.apellido, Validators.required],
      username: [this.data.user.username],
      //password: [data.user.password],
      habilitado: [this.data.user.habilitado, Validators.required],
      rol: [this.data.user.rol, Validators.required],
      idUserAdmin: [this.authService.getUserId()],
      tiendaId: [this.data.user.tiendaId],
    });
  }

  loadTiendas(): void {
    this.tiendaService.getTiendas(this.authService.getUsername(), true).subscribe({
      next: (tiendas) => {
        this.tiendas = tiendas.tiendas;
      },
      error: (err) => {
        this.notyf.error('Error al cargar tiendas');
        console.error(err);
      },
    });
  }

  onFileSelected(event: Event): void {
    this.loadingModal.showLoading('Cargando usuarios...')
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      console.log('Archivo seleccionado:', file);
  
      const formData = new FormData();
      formData.append('archivo', file); 

      this.userService.cargarUsuario(formData).subscribe({
        next: (response) => {
          this.notyf.success('Proceso de carga masiva completado');
          this.loadingModal.hideLoading();
          this.openModalCargaMasiva(response);
        },
        error: (err) => {
          this.loadingModal.hideLoading();
          console.error('Error al cargar el archivo:', err);
          this.notyf.error('Error al cargar el archivo');
        },
        complete: () => {
          this.dialogRef.close();
        }
      });
    }
  }

  openModalCargaMasiva(response: any) {
    const dialogRef = this.dialog.open(ModalCargaComponent, {
      width: '420px',
      data: { response }
    });
  }
}
