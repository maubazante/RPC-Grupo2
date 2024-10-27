import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-reports-form',
  templateUrl: './reports-form.component.html',
  styleUrl: './reports-form.component.css'
})
export class ReportsFormComponent {
  filtroForm: FormGroup;
  filtrosExistentes: any = []; // Array que debe llenarse con los filtros existentes desde el backend
  isAdmin = true; // Ajusta esto según el perfil de usuario actual

  constructor(private fb: FormBuilder) {  
    this.filtroForm = this.fb.group({
      nombreFiltro: new FormControl(''),
      camposFiltrar: this.fb.group({
        codigoProducto: new FormControl(false),
        rangoFechas: new FormControl(false),
        estado: new FormControl(false),
        tienda: new FormControl(false)
      })
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.filtroForm.valid) {
      console.log(this.filtroForm.value);
    }
  }

  onCancel() {
    this.filtroForm.reset();
  }
}
