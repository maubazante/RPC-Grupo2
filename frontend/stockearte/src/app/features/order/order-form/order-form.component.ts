import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

interface Producto {
  id: string;
  nombre: string;
}

interface OrderFormData {
  order: any;
  productos: Producto[];
  action: string;
}

@Component({
  selector: 'app-order-form',
  templateUrl: './order-form.component.html',
  styleUrls: ['./order-form.component.css']
})
export class OrderFormComponent implements OnInit {
  orderForm: FormGroup;
  productos: Producto[];
  isEdit: boolean = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<OrderFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OrderFormData
  ) {
    this.productos = data.productos;
    this.isEdit = data.action === 'edit';
    this.orderForm = this.fb.group({
      estado: [{ value: this.data.order.estado || 'SOLICITADA', disabled: !this.isEdit }, Validators.required],
      observaciones: [{ value: this.data.order.observaciones || '', disabled: !this.isEdit }],
      ordenDeDespacho: [{ value: this.data.order.ordenDeDespacho || '', disabled: !this.isEdit }],
      fechaSolicitud: [{ value: this.data.order.fechaSolicitud || new Date(), disabled: !this.isEdit }, Validators.required],
      fechaRecepcion: [{ value: this.data.order.fechaRecepcion || '', disabled: !this.isEdit }],
      codigoArticulo: [this.data.order.codigoArticulo || '', Validators.required],
      color: [this.data.order.color || '', Validators.required],
      talle: [this.data.order.talle || '', Validators.required],
      cantidadSolicitada: [this.data.order.cantidadSolicitada || 1, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    
  }

  onSave(): void {
    if (this.orderForm.valid) {
      // Retornar el formulario al componente principal
      this.dialogRef.close(this.orderForm.value);
    }
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
