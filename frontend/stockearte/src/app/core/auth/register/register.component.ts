import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Notyf } from 'notyf';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css', 
})
export class RegisterComponent implements OnInit{
  registerForm: FormGroup;
  hide = true;
  notyf = new Notyf({duration: 2000, position: {x: 'right', y: 'top',},});

  constructor(private fb: FormBuilder, private router: Router) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  
  ngOnInit(): void {
    console.log("Register Component works!")
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const { username, password } = this.registerForm.value;
      this.notyf.success("Usuario registrado exitosamente")
      this.router.navigate(['/users']);
    } else {
      this.notyf.error("Ambos usuario y contraseña son requeridos")
    }
  }

  login() {
    this.router.navigate(['/login'])
  }
  
}
