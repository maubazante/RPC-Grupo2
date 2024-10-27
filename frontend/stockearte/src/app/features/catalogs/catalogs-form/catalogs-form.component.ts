import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProductsService } from '../../../core/services/products.service';
import { CatalogosService } from '../../../core/services/catalogs.service';
import { Catalogo } from '../../../shared/types/Catalogo';
import { Producto } from '../../../shared/types/Producto';
import { AuthService } from '../../../core/services/auth.service';


@Component({
  selector: 'app-catalogs-form',
  templateUrl: './catalogs-form.component.html',
  styleUrls: ['./catalogs-form.component.css']
})
export class CatalogsFormComponent implements OnInit {
  catalog: Catalogo;
  isEditMode: boolean;
  availableProducts: Producto[] = []; // Productos disponibles para selección

  constructor(
    public dialogRef: MatDialogRef<CatalogsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private catalogsService: CatalogosService,
    private productsService: ProductsService,
    private authService: AuthService
  ) {
    this.isEditMode = !!data.catalog;
    this.catalog = data.catalog ? { ...data.catalog } : { nombre: '', descripcion: '', habilitado: true, productos: [] };
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productsService.getProductos(this.authService.getUsername(), true).subscribe((products) => {
      this.availableProducts = products.productos;
    });
  }

  compareProducts(p1: Producto, p2: Producto): boolean {
    return p1 && p2 ? p1.id === p2.id : p1 === p2;
  }

  saveCatalog(): void {
    if (this.isEditMode) {
      this.catalogsService.updateCatalogo(this.catalog.id!, this.catalog).subscribe(() => this.dialogRef.close(true));
    } else {
      this.catalogsService.createCatalogo(this.catalog).subscribe(() => this.dialogRef.close(true));
    }
  }
}
