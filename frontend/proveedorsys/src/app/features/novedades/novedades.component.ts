import { Component, OnInit } from '@angular/core';

interface Novedad {
  codigoProducto: string;
  descripcion: string;
  color: string;
  talle: string;
  cantidad: number;
  fecha: Date;
}

@Component({
  selector: 'app-novedades',
  templateUrl: './novedades.component.html',
  styleUrls: ['./novedades.component.css']
})
export class NovedadesComponent implements OnInit {
  novedades: Novedad[] = [
    { codigoProducto: 'A001', descripcion: 'Remera básica', color: 'Rojo', talle: 'M', cantidad: 50, fecha: new Date('2023-09-15') },
    { codigoProducto: 'A002', descripcion: 'Pantalón Jean', color: 'Azul', talle: 'L', cantidad: 30, fecha: new Date('2023-09-18') }
  ];

  displayedColumns: string[] = ['codigoProducto', 'descripcion', 'color', 'talle', 'cantidad', 'fecha', 'acciones'];

  constructor() {}

  ngOnInit(): void {}

  aceptarNovedad(codigo: string): void {
    // TODO: reemplazar este mock
    alert(`Producto con código ${codigo} aceptado.`);
  }
}
