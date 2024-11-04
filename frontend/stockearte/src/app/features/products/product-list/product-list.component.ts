import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Producto } from '../../../shared/types/Producto';
import { MatDialog } from '@angular/material/dialog';
import { ProductFormComponent } from '../product-form/product-form.component';
import { Notyf } from 'notyf';
import { ProductsService } from '../../../core/services/products.service';
import { AuthService } from '../../../core/services/auth.service';
import { ModalAction } from '../../../shared/types/ModalAction';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { StoresService } from '../../../core/services/stores.service';
import { Tienda } from '../../../shared/types/Tienda';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'cantidad', 'foto', 'edit', 'erase'];
  dataSource = new MatTableDataSource<Producto>();
  tiendas: Tienda[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;
  soloHabilitados: boolean = true;
  searchTerm$ = new Subject<string>();

  private subscriptions: Subscription[] = [];
  searchTerm!: string;

  constructor(
    private productsService: ProductsService,
    private tiendaService: StoresService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private AuthService: AuthService
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.AuthService.isAdmin();
    this.loadProductsByUsername();
    this.loadTiendas();

    this.searchTerm$.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(searchTerm => {
      this.searchTerm = searchTerm;
      this.filterProducts(searchTerm);
    });

    this.dataSource.filterPredicate = (data: Producto, filter: string) => {
      return data.codigo.toLowerCase().includes(filter) ||
        data.nombre.toLowerCase().includes(filter);
    };
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }


  loadTiendas(): void {
    const sub = this.tiendaService.getTiendas(this.AuthService.getUsername(), this.soloHabilitados).subscribe({
      next: (tiendas) => {
        this.tiendas = tiendas.tiendas;
      },
      error: (err) => {
        this.notyf.error('Error al cargar tiendas');
        console.error(err);
      },
      complete: () => {
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(sub);
  }

  loadProductsByUsername(): void {
    const sub = this.productsService.getProductos(this.AuthService.getUsername(), this.soloHabilitados).subscribe({
      next: (products) => {
        this.dataSource.data = products.productos;
      },
      error: (err) => {
        this.notyf.error('Error al cargar productos');
        console.error(err);
      },
      complete: () => {
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(sub);
  }

  editProduct(product: Producto): void {
    console.log(product);
    const dialogRef = this.dialog.open(ProductFormComponent, {
      width: '400px',
      data: { product: product, tiendas: this.tiendas, action: ModalAction.EDIT }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateProduct(result);
      }
    });
    this.subscriptions.push(sub);
  }

  createProduct(): void {
    const dialogRef = this.dialog.open(ProductFormComponent, {
      width: '400px',
      data: { tiendas: this.tiendas, action: ModalAction.CREATE }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const createSub = this.productsService.createProduct(result).subscribe({
          next: (response) => {
            response.message.includes('Error') ? this.notyf.error(response) : this.notyf.success(response);
            this.ngOnInit();
          },
          error: (err) => {
            this.notyf.error('Error al crear producto');
            console.error(err);
          }
        });
        this.subscriptions.push(createSub);
      }
    });
    this.subscriptions.push(sub);
  }

  updateProduct(product: Producto): void {
    console.log(product)
    const sub = this.productsService.modifyProduct(product).subscribe({
      next: (updatedProduct) => {
        updatedProduct.message.includes('Error') ? this.notyf.error(updatedProduct) : this.notyf.success(updatedProduct);
      },
      error: (err) => {
        this.notyf.error('Error al actualizar producto');
        console.error(err);
      },
      complete: () => {
        this.ngOnInit();
      }
    });
    this.subscriptions.push(sub);
  }

  deleteProduct(id: string): void {
    const sub = this.productsService.deleteProduct(id).subscribe({
      next: (response: any) => {
        response.message.includes("Error") || response.message === "" ? this.notyf.error("El producto no ha podido ser eliminado") : this.notyf.success(response.message)
      },
      error: (err) => {
        this.notyf.error('Error al eliminar producto');
        console.error(err);
      },
      complete: () => {
        this.ngOnInit()
      }
    });
    this.subscriptions.push(sub);

  }

  filterProducts(searchTerm: string): void {
    this.dataSource.filter = searchTerm.trim().toLowerCase();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.searchTerm$.next('');
    this.ngOnInit();
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
    if(inputElement.value === "") this.clearSearch();
  }

  toggleHabilitadas(event: any): void {
    this.soloHabilitados = event.checked;
    this.ngOnInit();
  }

}
