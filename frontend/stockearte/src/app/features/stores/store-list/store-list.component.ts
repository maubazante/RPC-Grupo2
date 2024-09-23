import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Tienda } from '../../../shared/types/Tienda';
import { MatDialog } from '@angular/material/dialog';
import { StoreFormComponent } from '../store-form/store-form.component';
import { Notyf } from 'notyf';
import { StoresService } from '../../../core/services/stores.service';
import { UsersService } from '../../../core/services/users.service';
import { Usuario } from '../../../shared/types/Usuario';

@Component({
  selector: 'app-store-list',
  standalone: false,
  templateUrl: './store-list.component.html',
  styleUrl: './store-list.component.css'
})
export class StoreListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'codigo', 'direccion', 'ciudad', 'provincia', 'habilitada', 'edit', 'erase'];
  dataSource: Tienda[] = []; // Aquí se almacenan las tiendas obtenidas
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });

  constructor(
    private tiendaService: StoresService, // Servicio para las tiendas
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadTiendas(); // Carga inicial de tiendas
  }

  loadTiendas(): void {
    this.tiendaService.getTiendas().subscribe({
      next: (tiendas) => {
        this.dataSource = tiendas.tiendas;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.notyf.error('Error al cargar tiendas');
        console.error(err);
      }
    });
  }

  editTienda(tienda: Tienda): void {
    const dialogRef = this.dialog.open(StoreFormComponent, {
      width: '450px',
      data: { tienda: tienda }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateTienda(result); // Si el formulario retorna un resultado, actualiza la tienda
      }
    });
  }

  createTienda(): void {
    const dialogRef = this.dialog.open(StoreFormComponent, {
      width: '400px',
      data: { tienda: {} as Tienda } // Para crear una nueva tienda
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.tiendaService.createStore(result).subscribe({
          next: (newTienda) => {
            this.dataSource.push(newTienda); // Agregar la nueva tienda al dataSource
            this.notyf.success('Tienda creada con éxito');
          },
          error: (err) => {
            this.notyf.error('Error al crear tienda');
            console.error(err);
          }
        });
      }
    });
  }

  updateTienda(tienda: Tienda): void {
    this.tiendaService.modifyStore(tienda).subscribe({
      next: (updatedTienda) => {
        this.notyf.success(updatedTienda);
      },
      error: (err) => {
        this.notyf.error('Error al actualizar tienda');
        console.error(err);
      },
      complete: () => {
        this.loadTiendas();
      }
    });
  }

  // No funciona
  deleteTienda(id: string): void {
    if (confirm('Eliminar tienda no funcionará esta entrega')) {
      this.tiendaService.deleteStore(id).subscribe({
        next: () => {
          // this.notyf.success('Tienda eliminada con éxito');
        },
        error: (err) => {
          this.notyf.error('Error al eliminar tienda');
          console.error(err);
        }
      });
    }
  }
}
