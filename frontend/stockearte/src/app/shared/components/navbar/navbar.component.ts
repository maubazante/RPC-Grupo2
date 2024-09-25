import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  
  isAdmin: boolean = false;
  constructor(private authService: AuthService) {
  }
  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
  }

  logout = () => { this.authService.logout() }
}
