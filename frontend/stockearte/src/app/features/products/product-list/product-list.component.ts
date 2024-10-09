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

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'cantidad', 'foto', 'edit', 'erase'];
  dataSource: Producto[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;
  searchTerm$ = new Subject<string>();

  private subscriptions: Subscription[] = [];
  searchTerm!: string;

  constructor(
    private productsService: ProductsService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private AuthService: AuthService
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.AuthService.isAdmin();
    this.isAdmin ? this.loadAllProducts() : this.loadProductsByUsername();
    
    this.searchTerm$.pipe(
      debounceTime(300),
      distinctUntilChanged() 
    ).subscribe(searchTerm => {
      this.searchTerm = searchTerm;
      this.filterProducts(searchTerm);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadAllProducts(): void {
    const sub = this.productsService.getAllProductos().subscribe({
      next: (products) => {
        this.dataSource = products.productos;
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

  loadProductsByUsername(): void {
    const sub = this.productsService.getProductos(this.AuthService.getUsername()).subscribe({
      next: (products) => {
        this.dataSource = products.productos;
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
      data: { product: product, action: ModalAction.EDIT }
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
      data: { product: {} as Producto, action: ModalAction.CREATE }
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
    if (confirm('Eliminar no funcionará esta entrega')) {
      const sub = this.productsService.deleteProduct(id).subscribe({
        next: () => {
          // this.dataSource = this.dataSource.filter(p => p.id !== id);
          // this.notyf.success('Producto eliminado con éxito');
        },
        error: (err) => {
          this.notyf.error('Error al eliminar producto');
          console.error(err);
        }
      });
      this.subscriptions.push(sub);
    }
  }

  filterProducts(searchTerm: string): void {
    this.dataSource = this.dataSource.filter(product =>
      product.nombre.toLowerCase().includes(searchTerm.toLowerCase())
    );
    // this.productsService.searchProducts(searchTerm).subscribe(...);
  }

  clearSearch(): void {
    this.searchTerm = ''; 
    this.searchTerm$.next(''); 
    this.loadAllProducts(); 
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
  }
  
  

}
