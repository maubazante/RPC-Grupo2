import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Tienda } from '../../../shared/types/Tienda';
import { Usuario } from '../../../shared/types/Usuario';

@Component({
  selector: 'app-store-form',
  standalone: false,
  templateUrl: './store-form.component.html',
  styleUrl: './store-form.component.css'
})
export class StoreFormComponent {
  storeForm: FormGroup;
  usuarios: Usuario[];  

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<StoreFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { store: Tienda, usuarios: Usuario[] }
  ) {
    this.usuarios = data.usuarios || [];  
    this.storeForm = this.fb.group({
      id: [data.store.id],
      codigo: [data.store.codigo, Validators.required],
      direccion: [data.store.direccion],
      ciudad: [data.store.ciudad],
      provincia: [data.store.provincia],
      habilitado: [data.store.habilitado, Validators.required],
      usuario: [data.store.usuario]  // Puede ser null
    });
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.storeForm.valid) {
      const updatedStore = this.storeForm.value;
      this.dialogRef.close(updatedStore);  
    }
  }
}
