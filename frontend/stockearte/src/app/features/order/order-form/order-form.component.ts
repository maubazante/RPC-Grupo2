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
  hasOrderDespacho: boolean = false;

  constructor(
    private fb: FormBuilder,
    private orderService: OrderService,
    public dialogRef: MatDialogRef<OrderFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { order: any, productos: Producto[], action: string }
  ) {
    this.productos = data.productos || [];
    let estadoValue = data.order.estado || 'SOLICITADA';
    this.isEdit = data.action === ModalAction.EDIT;
    this.hasOrderDespacho = data.order.id_orden_despacho != null;
    console.log(this.data.order);
    this.orderForm = this.fb.group({
      id: [null],
      estado: [{ value: estadoValue, disabled: !this.isEdit || !this.hasOrderDespacho }, Validators.required],
      observaciones: [{ value: data.order.observaciones, disabled: !this.isEdit }],
      id_orden_despacho: [{ value: data.order.id_orden_despacho, disabled: true }],
      fechaSolicitud: [{ value: data.order.fechaSolicitud || null, disabled: !this.isEdit }],
      fechaRecepcion: [{ value: data.order.fechaRecepcion, disabled: !this.isEdit }],
      codigoArticulo: [this.data.order.codigoArticulo || '', Validators.required],
      color: [this.data.order.color || '', Validators.required],
      talle: [this.data.order.talle || '', Validators.required],
      cantidadSolicitada: [this.data.order.cantidadSolicitada || 1, [Validators.required, Validators.min(0)]],
      tienda: [1]
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
      console.log(orderData);
      this.dialogRef.close(orderData);
    }
  }
}
