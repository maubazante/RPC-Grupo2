import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProductsService } from '../../../core/services/products.service';
import { CatalogosService } from '../../../core/services/catalogs.service';
import { Catalogo } from '../../../shared/types/Catalogo';
import { Producto } from '../../../shared/types/Producto';
import { AuthService } from '../../../core/services/auth.service';
import { Notyf } from 'notyf';


@Component({
  selector: 'app-catalogs-form',
  templateUrl: './catalogs-form.component.html',
  styleUrls: ['./catalogs-form.component.css']
})
export class CatalogsFormComponent implements OnInit {
  catalog: any;
  isEditMode: boolean;
  availableProducts: Producto[] = []; // Productos disponibles para selección
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });

  constructor(
    public dialogRef: MatDialogRef<CatalogsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private catalogsService: CatalogosService,
    private productsService: ProductsService,
    private authService: AuthService
  ) {
    this.isEditMode = !!data.catalog;
    this.catalog = data.catalog ? { ...data.catalog } : { id: 0, nombre: '', productosIds: [] };
    console.log(this.catalog);
    this.loadProducts();
  }

  ngOnInit(): void {

  }

  loadProducts(): void {
    this.productsService.getProductos(this.authService.getUsername(), true).subscribe((products) => {
      this.availableProducts = products.productos.map(product => ({
          ...product,
          id: Number(product.id)
      }));
  });
  
  }

  compareProducts(id1: number, id2: number): boolean {
    return id1 === id2;
  }
  

  saveCatalog(): void {
    this.catalog.tiendaId = this.authService.getTiendaId();
    this.catalog.productoIds = this.catalog.productosIds;

    if (this.isEditMode) {
      this.catalogsService.updateCatalogo(this.catalog.id!, this.authService.getUsername(), this.catalog).subscribe({
        next: () => {
          this.notyf.success('Catalogo actualizado exitosamente')
          this.dialogRef.close(true)
        },
        error: (error) => {
          console.error(error);
          this.notyf.error("Error actualizando catalogo");
        }
      });
    } else {
      this.catalogsService.createCatalogo(this.catalog, this.authService.getUsername()).subscribe({
        next: () => {
          this.notyf.success('Catalogo creado exitosamente')
          this.dialogRef.close(true)
        },
        error: (error: any) => {
          console.error(error);
          this.notyf.error("Error creando catalogo");
        }
      });
    }
  }
}
