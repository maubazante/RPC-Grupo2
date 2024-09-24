import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';  // Importa las animaciones estándar
import { routes } from './app.routes';  // Asegúrate de importar las rutas
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),  // Configuración de optimización de detección de cambios
    provideRouter(routes),  // Proveer las rutas
    provideAnimations(),
    provideHttpClient(),
  ]
};
