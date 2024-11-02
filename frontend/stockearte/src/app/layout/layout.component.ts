import { ChangeDetectionStrategy, Component, ViewChild } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css',
  changeDetection: ChangeDetectionStrategy.Default,
})
export class LayoutComponent {
  @ViewChild('drawer') drawer!: MatDrawer;
  showHamburgerMenu = false;
  
  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      const url = event.url;
      this.showHamburgerMenu = !(url.includes('/login') || url.includes('/register'));
      this.drawer.close();
    });
  }
}
