import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { INotyfNotificationOptions, Notyf } from 'notyf';
import { Rol } from '../../../shared/types/Rol';
import { IRegisterRequest } from '../../../shared/types/IRegisterRequest';
import { UsersService } from '../../services/users.service';
import { IRegisterResponse } from '../../../shared/types/IRegisterResponse';

const USUARIO_ADMIN = 1

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css', 
})
export class RegisterComponent {
  registerForm: FormGroup;
  hide = true;
  notyf = new Notyf({duration: 2000, position: {x: 'right', y: 'top',},});

  constructor(private fb: FormBuilder, private router: Router, private userService: UsersService) {
    this.registerForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      rol: [Rol.STOREMANAGER],
      idUserAdmin: [USUARIO_ADMIN]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const request: IRegisterRequest = this.registerForm.value;
      this.register(request);
    }
    else {
      this.notyf.error("Ambos usuario y contraseña son requeridos")
    }
  }

  register(request: IRegisterRequest) {
    console.log(request);
    this.userService.registerUsuario(request).subscribe({
      next: (response: IRegisterResponse) => {
        if(response.message.includes('Error')) this.notyf.error(response);
        else {
          sessionStorage.setItem("ROLE", request.rol);
          sessionStorage.setItem("USERNAME", request.username);
          this.notyf.success(response);
          this.router.navigate(['/users']);
        }
      },
      error: (error: string | Partial<INotyfNotificationOptions>) => {
        this.notyf.error(error);
        console.error(error);
      }
    })
  }

  login() {
    this.router.navigate(['/login']);
  }
}
