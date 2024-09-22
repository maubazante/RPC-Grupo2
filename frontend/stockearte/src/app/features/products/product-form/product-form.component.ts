import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Producto } from '../../../shared/types/Producto';
import { Tienda } from '../../../shared/types/Tienda';

@Component({
  selector: 'app-product-form',
  standalone: false,
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent {
  productForm: FormGroup;
  tiendas: Tienda[];  // Lista de tiendas para el select

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ProductFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { product: Producto, tiendas: Tienda[] }
  ) {
    this.tiendas = data.tiendas || [];
    this.productForm = this.fb.group({
      id: [data.product.id],
      nombre: [data.product.nombre, Validators.required],
      codigo: [data.product.codigo, Validators.required],
      foto: [data.product.foto],
      color: [data.product.color],
      talle: [data.product.talle],
      habilitado: [data.product.habilitado, Validators.required],
      tiendas: [data.product.tiendas.map(stock => stock.tienda)]
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
