import { Component, OnInit } from '@angular/core';
import { Catalogo } from '../../../shared/types/Catalogo';
import { CatalogosService } from '../../../core/services/catalogs.service';
import { AuthService } from '../../../core/services/auth.service';
import { CatalogsFormComponent } from '../catalogs-form/catalogs-form.component';
import { MatDialog } from '@angular/material/dialog';
import { Notyf } from 'notyf';

@Component({
  selector: 'app-catalogs-list',
  templateUrl: './catalogs-list.component.html',
  styleUrls: ['./catalogs-list.component.css']
})
export class CatalogsListComponent implements OnInit {
  dataSource: Catalogo[] = [];
  displayedColumns: string[] = ['id', 'nombre', 'export', 'edit', 'erase'];
  searchTerm: string = '';
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });

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
    this.notyf.success('Cargando PDF...')
    this.catalogosService.exportCatalogoToPDF(id, this.authService.getUsername()).subscribe((blob) => {
      blob.text().then((text) => {
        const jsonResponse = JSON.parse(text);
  
        if (jsonResponse && jsonResponse.pdfBase64) {
          // Decodifica el base64 y crea un Blob en formato PDF
          const byteCharacters = atob(jsonResponse.pdfBase64);
          const byteNumbers = new Array(byteCharacters.length);
          for (let i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
          }
          const byteArray = new Uint8Array(byteNumbers);
          const pdfBlob = new Blob([byteArray], { type: 'application/pdf' });
  
          const url = URL.createObjectURL(pdfBlob);
          window.open(url);
        } else {
          this.notyf.error('Fijate vos que hiciste mal')
          console.error('El JSON no contiene un campo "pdfBase64".');
        }
      });
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
