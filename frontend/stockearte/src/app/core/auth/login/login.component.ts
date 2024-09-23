import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css'; // for React, Vue and Svelte

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  loginForm: FormGroup;
  hide = true;
  notyf = new Notyf({duration: 2000, position: {x: 'right', y: 'top',},});

  constructor(private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      
      if (username === 'root' && password === 'root') {
        this.notyf.success("Usuario ingresado exitosamente")
        this.router.navigate(['/users']);
      } else {
        this.notyf.error("Por favor verificá los datos de logueo")
      }
    } else {
      this.notyf.error("Ambos usuario y contraseña son requeridos")
    }
  }

  register() {
    this.router.navigate(['/register'])
  }
}
