import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Tienda } from '../../../shared/types/Tienda';
import { Usuario } from '../../../shared/types/Usuario';
import { UsersService } from '../../../core/services/users.service';
import { ModalAction } from '../../../shared/types/ModalAction';

@Component({
  selector: 'app-store-form',
  standalone: false,
  templateUrl: './store-form.component.html',
  styleUrl: './store-form.component.css'
})
export class StoreFormComponent {
  storeForm: FormGroup;
  usuarios: Usuario[];
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<StoreFormComponent>,
    private userService: UsersService,
    @Inject(MAT_DIALOG_DATA) public data: { tienda: Tienda, usuarios: Usuario[], action: string }
  ) {
    this.usuarios = data.usuarios || [];
    this.isEdit = data.action === ModalAction.EDIT;
    this.storeForm = this.fb.group({
      id: [this.data.tienda.id],
      codigo: [this.data.tienda.codigo],
      direccion: [this.data.tienda.direccion],
      ciudad: [this.data.tienda.ciudad],
      provincia: [this.data.tienda.provincia],
      habilitada: [this.data.tienda.habilitada, Validators.required],
      es_casa_central: [this.data.tienda.es_casa_central],
      usuarioId: null
    }); 
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
}
