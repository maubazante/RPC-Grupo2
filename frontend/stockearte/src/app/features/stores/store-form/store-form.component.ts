import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Tienda } from '../../../shared/types/Tienda';
import { Usuario } from '../../../shared/types/Usuario';
import { UsersService } from '../../../core/services/users.service';

@Component({
  selector: 'app-store-form',
  standalone: false,
  templateUrl: './store-form.component.html',
  styleUrl: './store-form.component.css'
})
export class StoreFormComponent {
  storeForm: FormGroup;
  usuarios: Usuario[];  
  usuario: Usuario | null; 
  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<StoreFormComponent>,
    private userService: UsersService,
    @Inject(MAT_DIALOG_DATA) public data: { tienda: Tienda, usuarios: Usuario[] }
  ) {
    this.usuarios = data.usuarios || [];  
    this.usuario = this.loadUsers();
    this.storeForm = this.fb.group({
      id: [this.data.tienda.id],
      codigo: [this.data.tienda.codigo],
      direccion: [this.data.tienda.direccion],
      ciudad: [this.data.tienda.ciudad],
      provincia: [this.data.tienda.provincia],
      habilitado: [this.data.tienda.habilitada, Validators.required],
      es_casa_central: [this.data.tienda.es_casa_central],
      usuarioId: ""
    }); 
  }

  ngOnInit() {
    this.storeForm.controls['usuario'].setValue(this.usuario)
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.storeForm.valid) {
      const updatedStore = this.storeForm.value;
      console.log(updatedStore)
      this.dialogRef.close(updatedStore);  
    }
  }

  loadUsers(): Usuario | null {
    let userReturn: Usuario | null = null;
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.usuarios = users.usuarios
      },
      error: (err) => {
        console.error(err);
      },
      complete: () => {
        this.usuarios.map((user) => {
          if(user.id === this.data.tienda.usuarioId) userReturn = user;
        })
      }
    });
    return userReturn;
  }
}
