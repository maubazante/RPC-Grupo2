import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Producto } from '../../../shared/types/Producto';
import { Tienda } from '../../../shared/types/Tienda';
import { ModalAction } from '../../../shared/types/ModalAction';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-product-form',
  standalone: false,
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  productForm: FormGroup;
  tiendas: Tienda[];  // Lista de tiendas para el select
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public dialogRef: MatDialogRef<ProductFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { product: Producto, tiendas: Tienda[], action: string }
  ) {
    this.tiendas = data.tiendas || [];
    this.isEdit = data.action === ModalAction.EDIT;
    this.productForm = this.fb.group({
      id: [data.product.id],
      nombre: [data.product.nombre, Validators.required],
      codigo: [data.product.codigo],
      foto: [data.product.foto, Validators.pattern(/https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/)],
      color: [data.product.color],
      talle: [data.product.talle],
      habilitado: [data.product.habilitado, Validators.required],
      idUserAdmin: [this.authService.getUserId()],
      // tiendas: [data.product.tiendas.map(stock => stock.tienda)]
    });
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.productForm.valid) {
      const updatedProduct = this.productForm.value;
      this.dialogRef.close(updatedProduct);
    }
  }
}
