import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ReportsFormComponent } from '../reports-form/reports-form.component';
import { ReportsService } from '../../../core/services/reports.service';
import { Filtro } from '../../../shared/types/Filtro';
import { AuthService } from '../../../core/services/auth.service';
import { provideNativeDateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.css'],
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReportsListComponent implements OnInit {

  dataSource: any[] = [];
  filtros: Filtro[] = [];
  selectedFiltro: any = null;
  displayedColumns: string[] = ['id', 'codigoArticulo', 'tienda', 'fechaSolicitud', 'estado'];
  activeFilters: any = {};


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

  // Modifica este método
  applyFiltro(): void {
    if (this.selectedFiltro) {
      // Asigna los filtros activos basados en el perfil seleccionado
      this.activeFilters = {
        filtroEstado: this.selectedFiltro.filtroEstado,
        filtroFecha: this.selectedFiltro.filtroFecha,
        filtroProducto: this.selectedFiltro.filtroProducto,
        filtroTienda: this.selectedFiltro.filtroTienda
      };

      // Aquí puedes llamar a un método para filtrar los reportes según los filtros activos
      this.filterReports();
    } else {
      // Si no hay perfil seleccionado, limpia los filtros activos
      this.activeFilters = {};
      this.loadReports();
    }
  }

  filterReports(): void {
    // Implementa la lógica para filtrar los reportes según los filtros activos
    const filtrosAplicados = {
      estado: this.activeFilters.filtroEstado ? this.selectedFiltro?.estadoSeleccionado : null,
      fechaDesde: this.activeFilters.filtroFecha ? this.selectedFiltro?.fechaDesde : null,
      fechaHasta: this.activeFilters.filtroFecha ? this.selectedFiltro?.fechaHasta : null,
      producto: this.activeFilters.filtroProducto ? this.selectedFiltro?.productoSeleccionado : null,
      tienda: this.activeFilters.filtroTienda ? this.selectedFiltro?.tiendaSeleccionada : null
    };

    // Llama al servicio para obtener los reportes filtrados
    // this.reportsService.getReportsByFilters(filtrosAplicados).subscribe((data: any[]) => {
    //   this.dataSource = data;
    // });
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
