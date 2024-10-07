import { Routes } from '@angular/router';
import { LoginComponent } from './core/auth/login/login.component';
import { RegisterComponent } from './core/auth/register/register.component';
import { AuthGuard } from './core/guards/app.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'users', loadChildren: () => import('./features/users/users.module').then(m => m.UsersModule) },
  { path: 'stores', loadChildren: () => import('./features/stores/stores.module').then(m => m.StoresModule) },
  { path: 'products', loadChildren: () => import('./features/products/products.module').then(m => m.ProductsModule) },
  { path: 'news', loadChildren: () => import('./features/news/news.module').then(m => m.NewsModule) },
  { path: 'orders', loadChildren: () => import('./features/order/orders.module').then(m => m.OrdersModule) },
  { path: '**', redirectTo: '/login' }
];
