import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared/shared.module';
import { StoresModule } from './features/stores/stores.module';
import { ProductsModule } from './features/products/products.module';
import { UsersModule } from './features/users/users.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    SharedModule,
    StoresModule,
    ProductsModule,
    UsersModule
  ]
})
export class AppModule { }
