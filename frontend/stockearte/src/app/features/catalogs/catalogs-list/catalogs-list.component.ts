import { ChangeDetectorRef, Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subject, Subscription } from 'rxjs';
import { ProductsService } from '../../../core/services/products.service';
import { Producto } from '../../../shared/types/Producto';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-catalogs-list',
  templateUrl: './catalogs-list.component.html',
  styleUrl: './catalogs-list.component.css'
})
export class CatalogsListComponent {
  isAdmin: boolean = false;
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'color', 'talle', 'habilitado', 'cantidad', 'foto', 'edit', 'erase'];
  dataSource: Producto[] = [];
  searchTerm$ = new Subject<string>();
  private subscriptions: Subscription[] = [];
  searchTerm!: string;

  constructor(
    private productsService: ProductsService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.isAdmin = this.authService.isAdmin();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.searchTerm$.next('');
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
  }

  createCatalog() {
    throw new Error('Method not implemented.');
  }

}
