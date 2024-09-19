import { Component } from '@angular/core';
import { PRODUCTOS_MOCK } from '../../../shared/mock/producto-mock';
import { Producto } from '../../../shared/types/Producto';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'foto'];
  productos: Producto[] = PRODUCTOS_MOCK;
}
