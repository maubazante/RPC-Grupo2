import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared/shared.module';
import { StoresModule } from './features/stores/stores.module';
import { ProductsModule } from './features/products/products.module';
import { UsersModule } from './features/users/users.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LayoutComponent } from './layout/layout.component';
import { RouterModule } from '@angular/router';
import { AuthModule } from './core/auth/auth.module';

@NgModule({
  declarations: [LayoutComponent],
  imports: [
    CommonModule,
    SharedModule,
    StoresModule,
    ProductsModule,
    UsersModule,
    AuthModule,
    MatSidenavModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    RouterModule
  ],
  exports: [LayoutComponent]
})
export class AppModule { }
