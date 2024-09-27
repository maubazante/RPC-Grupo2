import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Tienda } from '../../../shared/types/Tienda';
import { MatDialog } from '@angular/material/dialog';
import { StoreFormComponent } from '../store-form/store-form.component';
import { Notyf } from 'notyf';
import { StoresService } from '../../../core/services/stores.service';
import { AuthService } from '../../../core/services/auth.service';
import { debounceTime, distinctUntilChanged, Subject, Subscription } from 'rxjs';
import { ModalAction } from '../../../shared/types/ModalAction';

@Component({
  selector: 'app-store-list',
  standalone: false,
  templateUrl: './store-list.component.html',
  styleUrl: './store-list.component.css'
})
export class StoreListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'codigo', 'direccion', 'ciudad', 'provincia', 'habilitada', 'edit', 'erase'];
  dataSource: Tienda[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;
  private subscriptions: Subscription[] = [];
  searchTerm$ = new Subject<string>();
  searchTerm: string = '';


  constructor(
    private tiendaService: StoresService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadTiendas();

    this.subscriptions.push(
      this.searchTerm$.pipe(
        debounceTime(300),
        distinctUntilChanged()
      ).subscribe(searchTerm => {
        this.searchTerm = searchTerm;
        this.filterStores(searchTerm);
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadTiendas(): void {
    const sub = this.tiendaService.getTiendas().subscribe({
      next: (tiendas) => {
        this.dataSource = tiendas.tiendas;
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

  editTienda(tienda: Tienda): void {
    const dialogRef = this.dialog.open(StoreFormComponent, {
      width: '450px',
      data: { tienda: tienda, action: ModalAction.EDIT }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateTienda(result);
      }
    });
    this.subscriptions.push(sub);
  }

  createTienda(): void {
    const dialogRef = this.dialog.open(StoreFormComponent, {
      width: '400px',
      data: { tienda: {} as Tienda, action: ModalAction.CREATE }
    });

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const createSub = this.tiendaService.createStore(result).subscribe({
          next: (response) => {
            response.message.includes('Error') ? this.notyf.error(response) : this.notyf.success(response);
            this.loadTiendas();
          },
          error: (err) => {
            this.notyf.error('Error al crear tienda');
            console.error(err);
          }
        });
        this.subscriptions.push(createSub);
      }
    });
    this.subscriptions.push(sub);
  }

  updateTienda(tienda: Tienda): void {
    const sub = this.tiendaService.modifyStore(tienda).subscribe({
      next: (updatedTienda) => {
        updatedTienda.message.includes('Error') ? this.notyf.error(updatedTienda) : this.notyf.success(updatedTienda);
      },
      error: (err) => {
        this.notyf.error('Error al actualizar tienda');
        console.error(err);
      },
      complete: () => {
        this.loadTiendas();
      }
    });
    this.subscriptions.push(sub);
  }

  deleteTienda(id: string): void {
    if (confirm('Eliminar tienda no funcionará esta entrega')) {
      const sub = this.tiendaService.deleteStore(id).subscribe({
        next: () => {
          // this.notyf.success('Tienda eliminada con éxito');
        },
        error: (err) => {
          this.notyf.error('Error al eliminar tienda');
          console.error(err);
        }
      });
      this.subscriptions.push(sub);
    }
  }

  filterStores(searchTerm: string): void {
    this.dataSource = this.dataSource.filter(tienda =>
      tienda.codigo.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.searchTerm$.next('');
    this.loadTiendas();
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
  }
}
