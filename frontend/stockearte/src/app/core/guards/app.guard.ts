import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  currentPrivilegeLevel!: string;
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const allowedPrivilegeLevel = route.data['privilege'];
    
    if (this.authService.isAdmin()) {
      this.currentPrivilegeLevel = 'ADMIN';
    } else this.currentPrivilegeLevel = 'STOREMANAGER';

    if (!this.authService.isLogged() || allowedPrivilegeLevel.indexOf(this.currentPrivilegeLevel) < 0) {
      this.router.navigate(['/login']);
      return false; 
    }
    return true;
  }
} 
