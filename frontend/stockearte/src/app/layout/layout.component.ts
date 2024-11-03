import { ChangeDetectionStrategy, Component, ViewChild, OnInit } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LayoutComponent implements OnInit {
  @ViewChild('drawer') drawer!: MatDrawer;
  showHamburgerMenu = false;
  username: string | null = sessionStorage.getItem("USERNAME");
  nombre: string | null = sessionStorage.getItem("NOMBRE");
  apellido: string | null = sessionStorage.getItem("APELLIDO");
  codTienda: string | null = sessionStorage.getItem("CODTIENDA");
  casaCentral: string | null = sessionStorage.getItem("CASACENTRAL");

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      const url = event.url;
      this.showHamburgerMenu = !(url.includes('/login') || url.includes('/register'));
      this.drawer.close();
      this.updateUserdata();
      this.updateTiendaData();
    });
  }

  ngOnInit(): void {
    this.updateUserdata();
    this.updateTiendaData();
  }

  private updateUserdata(): void {
    this.username = sessionStorage.getItem("USERNAME");
    this.nombre = sessionStorage.getItem("NOMBRE");
    this.apellido = sessionStorage.getItem("APELLIDO");
  }

  private updateTiendaData(): void {
    this.codTienda = sessionStorage.getItem("CODTIENDA");
    this.casaCentral = sessionStorage.getItem("CASACENTRAL");
  }
}
