import { Component, Inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Producto } from '../../../shared/types/Producto';
import { ModalAction } from '../../../shared/types/ModalAction';
import { OrderService } from '../../../core/services/order.service';

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent {
  orderForm: FormGroup;
  productos: Producto[];  // Lista de productos para el select
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private orderService: OrderService,
    public dialogRef: MatDialogRef<OrderFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { order: any, productos: Producto[], action: string }
  ) {
    this.productos = data.productos || [];
    this.isEdit = data.action === ModalAction.EDIT;
    this.orderForm = this.fb.group({
      id: [data.order.id || null],
      estado: [data.order.estado || 'SOLICITADA', Validators.required],
      observaciones: [data.order.observaciones],
      ordenDeDespacho: [data.order.ordenDeDespacho],
      fechaSolicitud: [data.order.fechaSolicitud || new Date().toISOString()],
      fechaRecepcion: [data.order.fechaRecepcion],
      items: this.fb.array(data.order.items ? data.order.items.map((item: any) => this.createItem(item)) : [this.createItem()])
    });
  }

  // Getter para acceder a los items dentro del formArray
  items(): FormArray {
    return this.orderForm.get('items') as FormArray;
  }

  // Crear un FormGroup para cada item
  createItem(item: any = {}): FormGroup {
    return this.fb.group({
      codigoArticulo: [item.codigoArticulo || '', Validators.required],
      color: [item.color || '', Validators.required],
      talle: [item.talle || '', Validators.required],
      cantidadSolicitada: [item.cantidadSolicitada || 1, [Validators.required, Validators.min(1)]]
    });
  }

  // Agregar un nuevo item
  addItem(): void {
    this.items().push(this.createItem());
  }

  // Eliminar un item específico
  removeItem(index: number): void {
    if (this.items().length > 1) {
      this.items().removeAt(index);
    }
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.orderForm.valid) {
      const orderData = this.orderForm.value;
      this.dialogRef.close(orderData);
    }
  }
}
