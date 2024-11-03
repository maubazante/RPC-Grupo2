import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isAdmin: boolean = false;
  username: string | null = sessionStorage.getItem("USERNAME");

  constructor(private authService: AuthService) {
    this.isAdmin = this.authService.isAdmin();
  }

  ngOnInit(): void {
    this.updateUserdata();
    console.log('Username from sessionStorage:', this.username); // Verifica que username tenga valor
  }

  private updateUserdata(): void {
    this.username = sessionStorage.getItem("USERNAME");
  }

  logout = () => {
    this.authService.logout();
    sessionStorage.clear();
  }
}
