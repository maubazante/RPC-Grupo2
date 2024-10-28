import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Notyf } from 'notyf';
import { ReportsService } from '../../../core/services/reports.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-reports-form',
  templateUrl: './reports-form.component.html',
  styleUrls: ['./reports-form.component.css']
})
export class ReportsFormComponent implements OnInit {
  filtroForm: FormGroup;
  isEdit: boolean = false;
  isAdmin: boolean = false;
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ReportsFormComponent>,
    private reportsService: ReportsService,
    private authService: AuthService,
    @Inject(MAT_DIALOG_DATA) public data: any // recibir información para edición
  ) {
    this.filtroForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      filtroEstado: [false],
      filtroFecha: [false],
      filtroProducto: [false],
      filtroTienda: [{value: false, disabled: !this.isAdmin}],
      id: [null],
      fkUsuariosId: Number(this.authService.getUserId()),
      userId: Number(this.authService.getUserId())
    });
  }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.isEdit = this.data.selectedProfile ? true : false;
    if (this.isEdit && this.data.selectedProfile) {
      this.filtroForm.patchValue(this.data.selectedProfile);
    }
  }

  onSave(): void {
    if (this.filtroForm.valid) {
      const filtroData = this.filtroForm.value;
      this.isEdit ? this.modificarFiltro(filtroData) : this.agregarFiltro(filtroData)
    } else {
      this.notyf.error('Complete todos los campos requeridos');
    }
  }

  agregarFiltro(filtroData: any) {
    this.reportsService.agregarFiltro(filtroData).subscribe({
      next: (response) => {
        this.notyf.success('Filtro guardado exitosamente');
        this.dialogRef.close(filtroData);
      },
      error: () => this.notyf.error('Error al guardar el filtro'),
      complete: () => console.log('Agregar filtro completado')
    });
  }
  
  modificarFiltro(filtroData: any) {
    this.reportsService.modificarFiltro(filtroData.id, filtroData).subscribe({
      next: (response) => {
        this.notyf.success('Filtro guardado exitosamente');
        this.dialogRef.close(filtroData);
      },
      error: () => this.notyf.error('Error al guardar el filtro'),
      complete: () => console.log('Modificar filtro completado')
    });
  }

  borrarFiltro() {
    this.reportsService.eliminarFiltro(this.data.selectedProfile.id).subscribe({
      next: (response) => {
        this.notyf.success('Filtro eliminado exitosamente');
        this.dialogRef.close();
      },
      error: () => this.notyf.error('Error al eliminar el filtro'),
      complete: () => console.log('Eliminar filtro completado')
    });
  }
  
  onCancel(): void {
    this.dialogRef.close();
  }
}
