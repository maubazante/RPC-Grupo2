import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Tienda } from '../../../shared/types/Tienda';
import { MatDialog } from '@angular/material/dialog';
import { StoreFormComponent } from '../store-form/store-form.component';
import { Notyf } from 'notyf';
import { StoresService } from '../../../core/services/stores.service';
import { AuthService } from '../../../core/services/auth.service';
import { debounceTime, distinctUntilChanged, finalize, Subject, Subscription } from 'rxjs';
import { ModalAction } from '../../../shared/types/ModalAction';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-store-list',
  standalone: false,
  templateUrl: './store-list.component.html',
  styleUrl: './store-list.component.css'
})
export class StoreListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'codigo', 'direccion', 'ciudad', 'provincia', 'habilitada', 'edit', 'erase'];
  dataSource = new MatTableDataSource<Tienda>();
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;
  soloHabilitados: boolean = true;
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

    this.dataSource.filterPredicate = (data: Tienda, filter: string) => {
      return data.codigo?.toLowerCase().includes(filter) ||
        data.ciudad.toLowerCase().includes(filter);
    };
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadTiendas(): void {
    const sub = this.tiendaService.getTiendas(this.authService.getUsername(), this.soloHabilitados).subscribe({
      next: (tiendas) => {
        this.dataSource.data = tiendas.tiendas;
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
        const createSub = this.tiendaService.createStore(result).pipe(finalize(() => this.loadTiendas())).subscribe({
          next: (response) => {
            response.message.includes('Error') ? this.notyf.error(response) : this.notyf.success(response);
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
    const sub = this.tiendaService.modifyStore(tienda).pipe(finalize(() => this.loadTiendas())).subscribe({
      next: (updatedTienda) => {
        updatedTienda.message.includes('Error') ? this.notyf.error(updatedTienda) : this.notyf.success(updatedTienda);
      },
      error: (err) => {
        this.notyf.error('Error al actualizar tienda');
        console.error(err);
      }
    });
    this.subscriptions.push(sub);
  }

  deleteTienda(tienda: any): void {
    const sub = this.tiendaService.deleteStore(tienda.codigo).pipe(finalize(() => this.loadTiendas())).subscribe({
      next: (response: any) => {
        this.notyf.success(response.message);
      },
      error: (err) => {
        this.notyf.error('Error al eliminar tienda');
        console.error(err);
      }
    });
    this.subscriptions.push(sub);

  }

  filterStores(searchTerm: string): void {
    this.dataSource.filter = searchTerm.trim().toLowerCase();
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
    if(inputElement.value === "") this.clearSearch();
  }

  toggleHabilitadas(event: any): void {
    this.soloHabilitados = event.checked;
    this.loadTiendas();
  }
}
