import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CatalogsFormComponent } from '../catalogs-form/catalogs-form.component';
import { Subscription } from 'rxjs';
import { CatalogosService } from '../../../core/services/catalogs.service';
import { Catalogo, CatalogoSOAP } from '../../../shared/types/Catalogo';
import { AuthService } from '../../../core/services/auth.service';
import { Notyf } from 'notyf';
import { ProductsService } from '../../../core/services/products.service';
import { ChangeDetectorRef } from '@angular/core';
import { LoadingModalService } from '../../../core/services/loading-modal.service';

@Component({
  selector: 'app-catalogs-list',
  templateUrl: './catalogs-list.component.html',
  styleUrls: ['./catalogs-list.component.css']
})
export class CatalogsListComponent implements OnInit, OnDestroy {
  dataSource: Catalogo[] = [];
  displayedColumns: string[] = ['id', 'nombre', 'export', 'edit', 'erase'];
  searchTerm: string = '';
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  private subscriptions = new Subscription();

  constructor(
    private catalogsService: CatalogosService,
    private dialog: MatDialog,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private loadingModal: LoadingModalService
  ) { }

  ngOnInit(): void {
    this.loadCatalogs();
  }

  loadCatalogs(): void {
    const catalogSubscription = this.catalogsService.getCatalogos(this.authService.getUsername()).subscribe({
      next: (data) => {
        this.dataSource = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
        this.notyf.error("Error obteniendo los catálogos")
      },
      complete: () => {
        console.log('Catalogs loaded successfully');
      }
    });
    this.subscriptions.add(catalogSubscription);
  }

  createCatalog(): void {
    const dialogRef = this.dialog.open(CatalogsFormComponent, {
      width: '500px',
      data: { catalog: null }
    });

    const dialogSubscription = dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.loadCatalogs();
        }
      },
      error: (err) => {
        console.error('Error after creating catalog', err);
      },
      complete: () => {
        console.log('Create catalog dialog closed');
      }
    });
    this.subscriptions.add(dialogSubscription);
  }

  editCatalog(catalog: CatalogoSOAP): void {
    const dialogRef = this.dialog.open(CatalogsFormComponent, {
      width: '500px',
      data: { catalog }
    });

    const dialogSubscription = dialogRef.afterClosed().subscribe({
      next: (result) => {
        if (result) {
          this.loadCatalogs();
        }
      },
      error: (err) => {
        console.error('Error after editing catalog', err);
      },
      complete: () => {
        console.log('Edit catalog dialog closed');
      }
    });
    this.subscriptions.add(dialogSubscription);
  }

  deleteCatalog(id: number): void {
    const deleteSubscription = this.catalogsService.deleteCatalogo(id, this.authService.getUsername()).subscribe({
      next: () => {
        this.notyf.success('Catalogo actualizado correctamente')
        this.loadCatalogs();
      },
      error: (err) => {
        this.notyf.error(err)
        console.error('Error deleting catalog', err);
      },
      complete: () => {
        console.log('Catalog deleted successfully');
      }
    });
    this.subscriptions.add(deleteSubscription);
  }

  exportCatalogToPDF(id: number): void {
    this.loadingModal.showLoading('Generando PDF...')
    const exportSubscription = this.catalogsService.exportCatalogoToPDF(id, this.authService.getUsername()).subscribe({
      next: (blob) => {
        // Decodifica el JSON si es necesario o trata el blob directamente si es PDF
        blob.text().then((text) => {
          const jsonResponse = JSON.parse(text);

          if (jsonResponse && jsonResponse.pdfBase64) {
            const byteCharacters = atob(jsonResponse.pdfBase64);
            const byteNumbers = new Array(byteCharacters.length);
            for (let i = 0; i < byteCharacters.length; i++) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            const pdfBlob = new Blob([byteArray], { type: 'application/pdf' });
            const url = URL.createObjectURL(pdfBlob);
            this.loadingModal.hideLoading();
            window.open(url);
          } else {
            this.loadingModal.hideLoading();
            this.notyf.error('Error generando PDF')
            console.error('El JSON no contiene un campo "pdfBase64".');
          }
        });
      },
      error: (err) => {
        console.error('Error exporting catalog to PDF', err);
      },
      complete: () => {
        console.log('Export to PDF completed');
      }
    });
    this.subscriptions.add(exportSubscription);
  }

  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchTerm = input.value;
    this.applyFilter();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.applyFilter();
  }

  applyFilter(): void {
    this.dataSource = this.dataSource.filter(catalog =>
      catalog.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();  // Desuscribirse de todas las suscripciones al destruir el componente
  }
}
