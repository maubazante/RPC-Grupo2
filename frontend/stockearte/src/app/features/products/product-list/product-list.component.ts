import { ChangeDetectorRef, Component } from '@angular/core';
import { Producto } from '../../../shared/types/Producto';
import { MatDialog } from '@angular/material/dialog';
import { ProductFormComponent } from '../product-form/product-form.component';
import { Notyf } from 'notyf';
import { ProductsService } from '../../../core/services/products.service';

@Component({
  selector: 'app-product-list',
  standalone: false,
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'foto', 'edit', 'erase'];
  dataSource: Producto[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });

  constructor(
    private productsService: ProductsService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    // Persistir nombre de usuario y ROL
    this.productsService.getProductos("baumamamam").subscribe({
      next: (products) => {
        this.dataSource = products.productos;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.notyf.error('Error al cargar productos');
        console.error(err);
      }
    });
  }

  editProduct(product: Producto): void {
    console.log(product)
    const dialogRef = this.dialog.open(ProductFormComponent, {
      width: '400px',
      data: { product: product }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log("RESULT:", result)
      if (result) {
        this.updateProduct(result);
        this.loadProducts();
      }
    });
  }

  createProduct(): void {
    const dialogRef = this.dialog.open(ProductFormComponent, {
      width: '400px',
      data: { product: {} as Producto }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productsService.createProduct(result).subscribe({
          next: (newProduct) => {
            this.dataSource.push(newProduct);  
            this.notyf.success('Producto creado con éxito');
          },
          error: (err) => {
            this.notyf.error('Error al crear producto');
            console.error(err);
          }
        });
      }
    });
  }

  updateProduct(product: Producto): void {
    this.productsService.modifyProduct(product).subscribe({
      next: (updatedProduct) => {
        const index = this.dataSource.findIndex(p => p.id === updatedProduct.id);
        if (index !== -1) {
          this.dataSource[index] = updatedProduct;  
        }
        this.notyf.success('Producto actualizado con éxito');
      },
      error: (err) => {
        this.notyf.error('Error al actualizar producto');
        console.error(err);
      }
    });
  }

  deleteProduct(id: string): void {
    if (confirm('Eliminar no funcionará esta entrega')) {
      this.productsService.deleteProduct(id).subscribe({
        next: () => {
          // this.dataSource = this.dataSource.filter(p => p.id !== id);
          // this.notyf.success('Producto eliminado con éxito');
        },
        error: (err) => {
          this.notyf.error('Error al eliminar producto');
          console.error(err);
        }
      });
    }
  }
}
