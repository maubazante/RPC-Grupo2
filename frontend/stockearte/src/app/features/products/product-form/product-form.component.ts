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
  isAdmin: boolean
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    public dialogRef: MatDialogRef<ProductFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { product: Producto, tiendas: Tienda[], action: string }
  ) {
    this.isAdmin = this.authService.isAdmin();
    this.tiendas = data.tiendas || [];
    this.isEdit = data.action === ModalAction.EDIT;
    this.productForm = this.fb.group({
      id: [data.product.id],
      nombre: [data.product.nombre, Validators.required],
      codigo: [data.product.codigo],
      foto: [data.product.foto, Validators.pattern(/https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/)],
      color: [data.product.color],
      talle: [data.product.talle],
      cantidad: [{value: Number(this.data.product.cantidad) || 0, disabled: this.authService.isAdmin()}, [Validators.required, Validators.min(0)]], // Control de cantidad
      habilitado: [data.product.habilitado, Validators.required],
      idUserAdmin: [this.authService.getUserId()],
      tiendaIds: [data.product.tiendaIds[0]]
    });
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.productForm.valid) {
      this.productForm.get('tiendaIds')?.setValue([this.productForm.get('tiendaIds')?.value])
      const updatedProduct = this.productForm.value;
      console.log(updatedProduct);
      this.dialogRef.close(updatedProduct);
    }
  }
}
