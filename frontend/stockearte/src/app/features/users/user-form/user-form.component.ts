import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Rol } from '../../../shared/types/Rol';
import { Tienda } from '../../../shared/types/Tienda';
import { Usuario } from '../../../shared/types/Usuario';

@Component({
  selector: 'app-user-form',
  standalone: false,
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.css'
})
export class UserFormComponent implements OnInit{
  userForm: FormGroup;
  roles = Object.values(Rol);
  tiendas: Tienda[] = []; 
  hide = true;  

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UserFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { user: Usuario, tiendas: Tienda[] }
  ) {
    console.log(data)
    this.tiendas = data.tiendas || []
    this.userForm = this.fb.group({
      id: [data.user.id],
      nombre: [data.user.nombre, Validators.required],
      apellido: [data.user.apellido, Validators.required],
      username: [data.user.username],
      password: [data.user.password],
      habilitado: [data.user.habilitado, Validators.required],
      rol: [data.user.rol, Validators.required],
      tienda: [null]  // Puede ser null
    });
  }

  ngOnInit() {

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
}
