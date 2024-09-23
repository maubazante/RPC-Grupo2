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
    @Inject(MAT_DIALOG_DATA) public data: { tienda: Tienda, usuarios: Usuario[] }
  ) {
    this.usuarios = data.usuarios || [];  
    this.storeForm = this.fb.group({
      id: [data.tienda.id],
      codigo: [{value: data.tienda.codigo, disabled: true}],
      direccion: [data.tienda.direccion],
      ciudad: [data.tienda.ciudad],
      provincia: [data.tienda.provincia],
      habilitado: [data.tienda.habilitada, Validators.required],
      es_casa_central: [data.tienda.es_casa_central]
      // usuario: [data.store.usuarioId]  // Puede ser null
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
