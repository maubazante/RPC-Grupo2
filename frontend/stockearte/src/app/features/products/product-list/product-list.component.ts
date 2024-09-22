import { Component } from '@angular/core';
import { PRODUCTOS_MOCK } from '../../../shared/mock/producto-mock';
import { Producto } from '../../../shared/types/Producto';
import { MatDialog } from '@angular/material/dialog';
import { ProductFormComponent } from '../product-form/product-form.component';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'foto', 'edit', 'erase'];
  productos: Producto[] = PRODUCTOS_MOCK;

  constructor(private dialog: MatDialog) {

  }

  edit(producto: any) {
    this.dialog.open(ProductFormComponent, {
      width: '50vw',
      data: {
        product: producto,
        tiendas: []
      }
    })
  }

  delete(producto: any) {
    
  }
}
