import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ReportsFormComponent } from '../reports-form/reports-form.component';
import { ReportsService } from '../../../core/services/reports.service';
import { Filtro } from '../../../shared/types/Filtro';
import { AuthService } from '../../../core/services/auth.service';
import { provideNativeDateAdapter } from '@angular/material/core';
import { StoresService } from '../../../core/services/stores.service';
import { ProductsService } from '../../../core/services/products.service';
import { Producto, ProductoArray } from '../../../shared/types/Producto';
import { Tienda, TiendaArray } from '../../../shared/types/Tienda';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.css'],
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReportsListComponent implements OnInit {
  isAdmin: boolean = false;
  products: Producto[] = [];
  tiendas: Tienda[] = [];
  dataSource: any[] = [];
  filtros: Filtro[] = [];
  selectedFiltro: any = null;
  filtroForm!: FormGroup;

  displayedColumns: string[] = ['id', 'codigoArticulo', 'tienda', 'fechaSolicitud', 'estado'];
  activeFilters: any = {};


  constructor(private reportsService: ReportsService, private dialog: MatDialog, private authService: AuthService, private tiendasService: StoresService,
    private productosService: ProductsService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadReports();
    this.loadFiltros();
    this.loadProducts();
    this.loadStores();
    this.loadForm();
  }

  loadReports(): void {
    // Lógica para cargar la lista de reportes
    this.reportsService.getReports().subscribe((data: any) => {
      this.dataSource = data;
    });
  }

  loadProducts() {
    this.productosService.getProductos(this.authService.getUsername(), true).subscribe({
      next: (productos: ProductoArray) => {
        this.products = productos.productos;
        console.log(this.products);
      }
    })
  }

  loadForm(): void {
    this.filtroForm = this.fb.group({
      tiendaId: [],
      productoId: []
    });
  }

  loadStores() {
    this.tiendasService.getTiendas(this.authService.getUsername(), true).subscribe({
      next: (tiendas: TiendaArray) => {
        this.tiendas = tiendas.tiendas;
        console.log(tiendas)
      }
    })
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
