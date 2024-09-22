import { Component, OnInit } from '@angular/core';
import { TIENDAS_MOCK } from '../../../shared/mock/tienda-mock';
import { Tienda } from '../../../shared/types/Tienda';

@Component({
  selector: 'app-store-list',
  standalone: false,
  templateUrl: './store-list.component.html',
  styleUrl: './store-list.component.css'
})
export class StoreListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'codigo', 'direccion', 'ciudad', 'provincia', 'usuario', 'habilitado', 'edit', 'erase'];
  tiendas: Tienda[] = TIENDAS_MOCK;  // Asignar el mock de datos

  ngOnInit () {
    console.log("Estoy en store component")
  }

}
