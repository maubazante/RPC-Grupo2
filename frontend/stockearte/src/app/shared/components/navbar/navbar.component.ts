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
  username: string | null = '';
  constructor(private authService: AuthService) {
    this.isAdmin = this.authService.isAdmin();
  }
  ngOnInit(): void {
    this.username = sessionStorage.getItem("USERNAME");
  }

  logout = () => { this.authService.logout(); sessionStorage.removeItem("USERNAME"); }
}
