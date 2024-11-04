import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
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
import { MatTableDataSource } from '@angular/material/table';
import { BehaviorSubject, combineLatest, map } from 'rxjs';

@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.css'],
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.Default,
})
export class ReportsListComponent implements OnInit {
  isAdmin: boolean = false;
  products: Producto[] = [];
  tiendas: Tienda[] = [];
  originalDataSource: any[] = [];
  dataSource = new MatTableDataSource<any>();

  activeFilters: { [key: string]: boolean } = {
    filtroProducto: false,
    filtroTienda: false,
    filtroEstado: false,
    filtroFecha: false
  };

  filtros: Filtro[] = [];
  selectedFiltro: any = null;
  filtroForm!: FormGroup;
  displayedColumns: string[] = ['id', 'codigoArticulo', 'cantidad', 'tienda', 'fechaSolicitud', 'estado'];
  private reportsSubject = new BehaviorSubject<any[]>([]);
  private filtersSubject = new BehaviorSubject<any>(null);


  constructor(
    private reportsService: ReportsService,
    private dialog: MatDialog,
    private authService: AuthService,
    private tiendasService: StoresService,
    private productosService: ProductsService,
    private cd: ChangeDetectorRef,
    private fb: FormBuilder
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadInitialData();
    this.setupReactiveFilters();

  }

  private initForm(): void {
    this.filtroForm = this.fb.group({
      productoId: [null],
      tiendaId: [null],
      estado: [null],
      fechaDesde: [null],
      fechaHasta: [null]
    });
  }

  private loadInitialData(): void {
    this.loadReports();
    this.loadProducts();
    this.loadStores();
    this.loadFiltros();
  }

  loadReports(): void {
    this.reportsService.getReports().subscribe((data: any) => {
      console.log(data);
      this.originalDataSource = data.filter((item: any) => item.tienda.id === this.authService.getTiendaId());
      this.dataSource.data = this.originalDataSource;
      this.reportsSubject.next(this.originalDataSource);
    });
  }

  loadProducts(): void {
    this.productosService.getProductos(this.authService.getUsername(), true).subscribe({
      next: (productos: ProductoArray) => {
        this.products = productos.productos;
      }
    });
  }

  loadStores(): void {
    this.tiendasService.getTiendas(this.authService.getUsername(), true).subscribe({
      next: (tiendas: TiendaArray) => {
        this.tiendas = tiendas.tiendas;
        console.log(tiendas)
      }
    });
  }

  loadFiltros(): void {
    this.reportsService.obtenerFiltros(this.authService.getUserId()).subscribe((profiles: any) => {
      this.filtros = profiles;
    });
  }

  applyFiltro(): void {
    if (this.selectedFiltro) {
      this.activeFilters = {
        filtroProducto: this.selectedFiltro.filtroProducto,
        filtroTienda: this.selectedFiltro.filtroTienda,
        filtroEstado: this.selectedFiltro.filtroEstado,
        filtroFecha: this.selectedFiltro.filtroFecha
      };

      // Emitir los valores actuales del formulario
      this.filtersSubject.next(this.filtroForm.value);
    } else {
      this.activeFilters = {
        filtroProducto: false,
        filtroTienda: false,
        filtroEstado: false,
        filtroFecha: false
      };
      this.filtroForm.reset();
      this.filtersSubject.next(null);
    }
  }

  private setupReactiveFilters(): void {
    combineLatest([
      this.reportsSubject,
      this.filtersSubject
    ]).pipe(
      map(([reports, filters]) => {
        if (!filters) return reports;

        let filteredData = [...reports];

        if (this.activeFilters['filtroProducto'] && filters.productoId) {
          filteredData = filteredData.filter(item =>
            item.producto[0].codigo === filters.productoId);
        }

        if (this.activeFilters['filtroTienda'] && filters.tiendaId) {
          filteredData = filteredData.filter(item =>
            item.tienda.id === Number(filters.tiendaId)
          );
        }

        if (this.activeFilters['filtroEstado'] && filters.estado) {
          filteredData = filteredData.filter(item =>
            item.estado === filters.estado
          );
        }

        if (this.activeFilters['filtroFecha'] && filters.fechaDesde && filters.fechaHasta) {
          const startDate = new Date(filters.fechaDesde);
          const endDate = new Date(filters.fechaHasta);

          filteredData = filteredData.filter(item =>
            this.isDateInRange(new Date(item.fechaSolicitud), startDate, endDate)
          );
        }

        return filteredData;
      })
    ).subscribe(filteredData => {
      this.dataSource.data = filteredData;
    });

    // Observa cambios en el formulario
    this.filtroForm.valueChanges.subscribe(filters => {
      this.filtersSubject.next(filters);
    });
  }

  private isSameDay(date1: Date, date2: Date): boolean {
    return date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate();
  }

  private isDateInRange(date: Date, startDate: Date, endDate: Date): boolean {
    const normalizedDate = new Date(
      date.getUTCFullYear(),
      date.getUTCMonth(),
      date.getUTCDate(),
      date.getUTCHours(),
    );
    const normalizedStart = new Date(
      startDate.getUTCFullYear(),
      startDate.getUTCMonth(),
      startDate.getUTCDate(),
      startDate.getUTCHours() - 3,
    );
    const normalizedEnd = new Date(
      endDate.getUTCFullYear(),
      endDate.getUTCMonth(),
      endDate.getUTCDate(),
      endDate.getUTCHours() - 3,
    );

    console.log("normalizedDate", normalizedDate);
    console.log("normalizedStart", normalizedStart);
    console.log("normalizedEnd", normalizedEnd);

    return normalizedDate >= normalizedStart && normalizedDate <= normalizedEnd;
  }


  openFilterModal(): void {
    const dialogRef = this.dialog.open(ReportsFormComponent, {
      width: '500px',
      data: { filtros: this.filtros, selectedProfile: this.selectedFiltro }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadInitialData();
      }
    });
  }
}