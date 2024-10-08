import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { OrderFormComponent } from '../order-form/order-form.component';
import { Notyf } from 'notyf';
import { AuthService } from '../../../core/services/auth.service';
import { ModalAction } from '../../../shared/types/ModalAction';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { OrderService } from '../../../core/services/order.service';
import { ProductsService } from '../../../core/services/products.service';
import { Producto, ProductoArray } from '../../../shared/types/Producto';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', "codigoArticulo", 'estado', 'observaciones', 'ordenDeDespacho', 'fechaSolicitud', 'fechaRecepcion', 'edit', 'erase'];
  dataSource: any[] = [];
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  searchTerm$ = new Subject<string>();
  productos: Producto[] = [];

  private subscriptions: Subscription[] = [];
  searchTerm!: string;

  constructor(
    private orderService: OrderService,
    private productsService: ProductsService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadAllOrders();
    this.loadProducts();

    this.searchTerm$.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(searchTerm => {
      this.searchTerm = searchTerm;
      this.filterOrders(searchTerm);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadAllOrders(): void {
    const sub = this.orderService.getAllOrders().subscribe({
      next: (orders: any) => {
        this.dataSource = orders;
      },
      error: (err) => {
        this.notyf.error('Error al cargar órdenes de compra');
        console.error(err);
      },
      complete: () => {
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(sub);
  }

  editOrder(order: any): void {
    console.log(order);
    const dialogRef = this.dialog.open(OrderFormComponent, {
      width: '400px',
      data: { order: order, productos: this.dataSource, action: ModalAction.EDIT }
    });
    

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.updateOrder(result);
      }
    });
    this.subscriptions.push(sub);
  }

  createOrder(): void {
    const dialogRef = this.dialog.open(OrderFormComponent, {
      panelClass: 'custom-dialog-container',
      width: '900px',
      data: { order: {}, productos: this.productos, action: ModalAction.CREATE }
    })

    const sub = dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const createSub = this.orderService.createOrder(result).subscribe({
          next: () => {
            this.notyf.success('Orden de compra creada con éxito');
            this.loadAllOrders();
          },
          error: (err: any) => {
            this.notyf.error('Error al crear la orden');
            console.error(err);
          }
        });
        this.subscriptions.push(createSub);
      }
    });
  }

  updateOrder(order: any): void {
    const sub = this.orderService.modifyOrder(order).subscribe({
      next: (updatedOrder: any) => {
        updatedOrder ? this.notyf.success('Orden actualizada con éxito') : this.notyf.success('Error actualizando orden');
      },
      error: (err: any) => {
        this.notyf.error('Error al actualizar la orden');
        console.error(err);
      },
      complete: () => {
        if(order.estado === "RECIBIDA") this.marcarOrderComoRecibida(order.id);
      }
    });
    this.subscriptions.push(sub);
  }

  deleteOrder(id: string): void {
    if (confirm('¿Seguro que deseas eliminar esta orden?')) {
      const sub = this.orderService.deleteOrder(id).subscribe({
        next: () => {
          this.notyf.success('Orden eliminada con éxito');
          this.loadAllOrders();
        },
        error: (err) => {
          this.notyf.error('Error al eliminar la orden');
          console.error(err);
        }
      });
      this.subscriptions.push(sub);
    }
  }

  loadProducts() {
    const subs = this.productsService.getProductos(this.authService.getUsername(), true).subscribe({
      next: (value: ProductoArray) => {
        this.productos = value.productos
      },
      error: (error) => {
        console.error(error);
      },
      complete: () => {
        subs.unsubscribe();
      }
    })
  }

  
  marcarOrderComoRecibida(id: string) {
    const subs = this.orderService.marcarOrderComoRecibida(id).subscribe({
      next: (response: any) => {
        response ? this.notyf.success('Se ha incorporado nuevo producto al stock!') : this.notyf.error("Error: No se ha devuelto respuesta") 
      },
      error: (err: any) => {
        console.log(err);
        this.notyf.error("Error del servidor")
      },
      complete: () => {
        subs.unsubscribe();
      }
    })
  }

  // Búsqueda de órdenes de compra, quizás se sumen a la entrega 3

  filterOrders(searchTerm: string): void {
    this.dataSource = this.dataSource.filter(order =>
      order.estado.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.searchTerm$.next('');
    this.loadAllOrders();
  }

  onSearchChange(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement && inputElement.value) {
      this.searchTerm$.next(inputElement.value);
    }
  }
}
