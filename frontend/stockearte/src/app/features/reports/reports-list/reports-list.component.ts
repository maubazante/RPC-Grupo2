import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ReportsFormComponent } from '../reports-form/reports-form.component';
import { ReportsService } from '../../../core/services/reports.service';
import { Filtro } from '../../../shared/types/Filtro';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.css']
})
export class ReportsListComponent implements OnInit {

  dataSource: any[] = [];
  filtros: Filtro[] = [];
  selectedFiltro: Filtro | null = null;
  displayedColumns: string[] = ['id', 'codigoArticulo', 'tienda', 'fechaSolicitud', 'estado'];

  constructor(private reportsService: ReportsService, private dialog: MatDialog, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadReports();
    this.loadFiltros();
  }

  loadReports(): void {
    // Lógica para cargar la lista de reportes
    this.reportsService.getReports().subscribe((data: any) => {
      this.dataSource = data;
    });
  }

  loadFiltros(): void {
    // Lógica para cargar perfiles de filtros guardados
    this.reportsService.obtenerFiltros(this.authService.getUserId()).subscribe((profiles: any) => {
      console.log(profiles);
      this.filtros = profiles;
    });
  }

  applyFiltro(): void {
    if (this.selectedFiltro) {
      // Aplicar los filtros del perfil seleccionado
      // this.reportsService.getReportsByFilter(this.selectedFiltro.nombre).subscribe((data: any[]) => {
      //   this.dataSource = data;
      // });
    }
  }

  openFilterModal(): void {
    const dialogRef = this.dialog.open(ReportsFormComponent, {
      width: '500px',
      data: { filtros: this.filtros, selectedProfile: this.selectedFiltro }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadReports();
        this.loadFiltros();
      }
    });
  }
}
