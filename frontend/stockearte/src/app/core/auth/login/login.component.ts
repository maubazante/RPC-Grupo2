import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css'; // for React, Vue and Svelte
import { UsersService } from '../../services/users.service';
import { ILoginRequest } from '../../../shared/types/ILoginRequest';
import { ILoginResponse } from '../../../shared/types/ILoginResponse';
import { Rol } from '../../../shared/types/Rol';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  loginForm: FormGroup;
  hide = true;
  notyf = new Notyf({ duration: 2000, position: { x: 'right', y: 'top', }, });

  constructor(private fb: FormBuilder, private router: Router, private userService: UsersService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.login(username, password);
    }
    else {
      this.notyf.error("Ambos usuario y contraseña son requeridos")
    }
  }


  register() {
    this.router.navigate(['/register'])
  }

  login(username: string, password: string) {
    let body: ILoginRequest = { username: username, password: password }
    this.userService.loginUsuario(body).subscribe({
      next: (response: ILoginResponse) => {
        if (response.errorMessage) this.notyf.error(response.errorMessage)
        else {
          sessionStorage.setItem("ROLE", response.rol);
          sessionStorage.setItem("USERNAME", response.username);
          sessionStorage.setItem("USER_ID", response.userId);
          sessionStorage.setItem("TIENDA_ID", response.tiendaId);
          this.notyf.success("Usuario ingresado exitosamente")
          response.rol === Rol.ADMIN ? this.router.navigate(['/users']) : this.router.navigate(['/products']);
        }
      },
      error: (error) => {
        this.notyf.error(error);
        console.error(error);
      }
    })
  }
}
