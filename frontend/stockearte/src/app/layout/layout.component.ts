import { ChangeDetectionStrategy, Component, ViewChild, OnInit } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LayoutComponent implements OnInit {
  @ViewChild('drawer') drawer!: MatDrawer;
  showHamburgerMenu = false;
  username: string | null = '';

  // Inyectar el AuthService
  constructor(private router: Router, private authService: AuthService) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      const url = event.url;
      this.showHamburgerMenu = !(url.includes('/login') || url.includes('/register'));
      this.drawer.close();
    });
  }

  ngOnInit(): void {
    this.username = this.authService.getUsername();
  }
}
