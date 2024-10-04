import { Component, OnInit } from '@angular/core';

interface OrdenCompra {
  codigo: string;
  color: string;
  talle: string;
  cantidad: number;
  estado: string;
}

@Component({
  selector: 'app-ordenes',
  templateUrl: './ordenes.component.html',
  styleUrls: ['./ordenes.component.css']
})
export class OrdenesComponent implements OnInit {
  ordenes: OrdenCompra[] = [
    { codigo: 'A001', color: 'Rojo', talle: 'M', cantidad: 10, estado: 'SOLICITADA' }
  ];
  displayedColumns: string[] = ['codigo', 'color', 'talle', 'cantidad', 'estado'];

  constructor() {}

  ngOnInit(): void {}

  nuevaOrden(): void { /* TODO */ }
}
