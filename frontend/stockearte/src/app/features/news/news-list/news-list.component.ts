import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Notyf } from 'notyf';
import { AuthService } from '../../../core/services/auth.service';
import { Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { NewsService } from '../../../core/services/news.service';
import { dataSourceNews } from '../datasource-temporal';
import { NewsFormComponent } from '../news-form/news-form.component';

@Component({
  selector: 'app-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.css']
})
export class NewsListComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['id', 'nombre', 'codigo', 'selectTallesColores'];
  dataSource: any[] = dataSourceNews;
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top' } });
  isAdmin: boolean = false;

  private subscriptions: Subscription[] = [];

  constructor(
    private newsService: NewsService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadAllNews();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  loadAllNews(): void {
    const sub = this.newsService.getAllNews().subscribe({
      next: (newsList) => {
        this.dataSource = newsList;
      },
      error: (err) => {
        this.notyf.error('Error al cargar las noticias');
        console.error(err);
      },
      complete: () => {
        this.cdr.detectChanges();
      }
    });
    this.subscriptions.push(sub);
  }

  openSelectionDialog(newsItem: any): void {
    const dialogRef = this.dialog.open(NewsFormComponent, {
      width: '400px',
      data: {
        id: newsItem.id,
        codigo: newsItem.codigo,
        nombre: newsItem.nombre,
        talle: newsItem.talle,
        color: newsItem.color,
        foto: newsItem.foto,
        cantidad: newsItem.cantidad,
        habilitado: newsItem.habilitado
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(result);
        this.updateNovedad(result);
      }
    });
  }

  updateNovedad(result: any) {
    this.newsService.modifyNews(result).subscribe({
      next: (response) => {
        response.message.includes('Error') ? this.notyf.error(response.message) : this.notyf.success(response.message);
      },
      error: (err) => {
        this.notyf.error("Error del servidor")
        console.log(err);
      },
      complete: () => {
        this.ngOnInit();
      }
    })

  }


  deleteNews(id: string): void {
    if (confirm('¿Seguro que deseas eliminar esta noticia?')) {
      const sub = this.newsService.deleteNews(id).subscribe({
        next: () => {
          this.notyf.success('Noticia eliminada con éxito');
          this.loadAllNews();
        },
        error: (err) => {
          this.notyf.error('Error al eliminar la noticia');
          console.error(err);
        }
      });
      this.subscriptions.push(sub);
    }
  }
}
