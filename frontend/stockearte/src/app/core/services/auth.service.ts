import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) {}
  
  public getUsername(): string | null {
    return sessionStorage.getItem("USERNAME");
  }

  public getRol(): string | null {
    return sessionStorage.getItem("ROLE");
  }

  public getUserId(): string | null {
    return sessionStorage.getItem("USER_ID");
  }

  public isLogged(): boolean {
    return this.getUsername() ? true : false;
  }

  public isAdmin(): boolean {
    if(!this.isLogged) return false;
    return this.getRol() === "ADMIN";
  }

  public isStoreManager(): boolean {
    if(!this.isLogged) return false;
    return this.getRol() === "STOREMANAGER";
  }

  public logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}
