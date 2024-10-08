import { Component, Inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { StoresService } from '../../../core/services/stores.service';
import { Tienda } from '../../../shared/types/Tienda';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.css']
})
export class NewsFormComponent {
  form: FormGroup;
  tiendas: Tienda[] = [];

  constructor(
    private fb: FormBuilder,
    private storesService: StoresService,
    private dialogRef: MatDialogRef<NewsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.form = this.fb.group({
      cantidad: [this.data.cantidad, [Validators.required, Validators.min(0), Validators.max(this.data.cantidad)]],
      tiendaId: [null, Validators.required]  // Campo para la selección de tienda
    });
  }

  ngOnInit(): void {
    console.log(this.data);
      // Cargar las tiendas desde el servicio
     this.storesService.getTiendas().subscribe((tiendas) => {
      this.tiendas = tiendas.tiendas;
    });
  }

  onSave(): void {
    if (this.form.valid) {
      console.log(this.form.value);
      this.dialogRef.close(this.form.value);
    }
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
