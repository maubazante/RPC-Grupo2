import { CommonModule } from "@angular/common";
import { MaterialModule } from "./shared/material.module";
import { OrdenesComponent } from "./features/ordenes/ordenes.component";
import { NgModule } from "@angular/core";
import { NovedadesComponent } from "./features/novedades/novedades.component";
import { ProveedorStockComponent } from "./features/proveedor-stock/proveedor-stock.component";
import { ReactiveFormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AppRoutingModule } from "./app.routes";
import { AppComponent } from "./app.component";

@NgModule({
    declarations: [AppComponent, OrdenesComponent, NovedadesComponent, ProveedorStockComponent],
    imports: [
        CommonModule,
        MaterialModule,
        ReactiveFormsModule,
        AppRoutingModule,
        BrowserModule,
        BrowserAnimationsModule,
    ],
    exports: [OrdenesComponent, NovedadesComponent, ProveedorStockComponent],
    bootstrap: [AppComponent]
})
export class AppModule {}