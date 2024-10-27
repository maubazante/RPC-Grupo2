import { Component, OnInit } from '@angular/core';
import { Catalogo } from '../../../shared/types/Catalogo';
import { CatalogosService } from '../../../core/services/catalogs.service';
import { AuthService } from '../../../core/services/auth.service';
import { CatalogsFormComponent } from '../catalogs-form/catalogs-form.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-catalogs-list',
  templateUrl: './catalogs-list.component.html',
  styleUrls: ['./catalogs-list.component.css']
})
export class CatalogsListComponent implements OnInit {
  dataSource: Catalogo[] = [];
  displayedColumns: string[] = ['id', 'nombre', 'export', 'edit', 'erase'];
  searchTerm: string = '';

  constructor(private catalogosService: CatalogosService, private authService: AuthService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadCatalogos();
  }

  loadCatalogos(): void {
    this.catalogosService.getCatalogos(this.authService.getUsername()).subscribe((data) => {
      this.dataSource = data;
    });
  }

  createCatalog(): void {
    const dialogRef = this.dialog.open(CatalogsFormComponent, {
      width: '500px',
      data: { catalog: null }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadCatalogos();
      }
    });
  }

  editCatalog(catalog: Catalogo): void {
    const dialogRef = this.dialog.open(CatalogsFormComponent, {
      width: '500px',
      data: { catalog }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadCatalogos();
      }
    });
  }

  deleteCatalog(id: number): void {
    this.catalogosService.deleteCatalogo(id).subscribe(() => this.loadCatalogos());
  }

  exportCatalogToPDF(id: number): void {
    this.catalogosService.exportCatalogoToPDF(id).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `catalogo_${id}.pdf`;
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }

  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchTerm = input.value;
    // Aplicar lógica de búsqueda
  }

  clearSearch(): void {
    this.searchTerm = '';
    // Limpiar lógica de búsqueda
  }
}
