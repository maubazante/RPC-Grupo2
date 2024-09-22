import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { RouterModule } from "@angular/router";

@NgModule({
    declarations: [NavbarComponent],
    imports: [CommonModule, 
        MatSidenavModule, 
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        RouterModule
    ],
    exports: [NavbarComponent]
})
export class SharedModule { }
