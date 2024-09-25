import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  
  isAdmin: boolean = false;
  constructor(private authService: AuthService) {
    this.isAdmin = this.authService.isAdmin();
  }

  logout = () => { this.authService.logout() }


}
