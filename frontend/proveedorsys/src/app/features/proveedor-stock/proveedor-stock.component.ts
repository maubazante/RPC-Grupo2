import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';

interface Producto {
  codigo: string;
  descripcion: string;
  color: string;
  talle: string;
  stock: number;
}

@Component({
  selector: 'app-proveedor-stock',
  templateUrl: './proveedor-stock.component.html',
  styleUrls: ['./proveedor-stock.component.css']
})
export class ProveedorStockComponent implements OnInit {
  productos: Producto[] = [
    { codigo: 'A001', descripcion: 'Remera básica', color: 'Rojo', talle: 'M', stock: 100 },
    { codigo: 'A002', descripcion: 'Pantalón Jean', color: 'Azul', talle: 'L', stock: 50 },
    { codigo: 'A003', descripcion: 'Camisa formal', color: 'Blanco', talle: 'S', stock: 30 }
  ];

  stockForm: FormGroup;

  displayedColumns: string[] = ['codigo', 'descripcion', 'color', 'talle', 'stock', 'acciones'];

  constructor(private fb: FormBuilder) {
    this.stockForm = this.fb.group({
      productos: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.cargarProductosEnFormulario();
  }

  cargarProductosEnFormulario(): void {
    const productosFormArray = this.stockForm.get('productos') as FormArray;
    this.productos.forEach(producto => {
      productosFormArray.push(this.fb.group({
        codigo: new FormControl(producto.codigo),
        descripcion: new FormControl(producto.descripcion),
        color: new FormControl(producto.color),
        talle: new FormControl(producto.talle),
        stock: new FormControl(producto.stock)
      }));
    });
  }

  get productosFormArray(): FormArray {
    return this.stockForm.get('productos') as FormArray;
  }

  actualizarStock(index: number): void {
    const productoActualizado = this.productosFormArray.at(index).value;
    alert(`El stock del producto ${productoActualizado.descripcion} (código: ${productoActualizado.codigo}) ha sido actualizado a ${productoActualizado.stock} unidades.`);
  }
}
