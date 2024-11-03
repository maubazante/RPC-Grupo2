import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { RouterModule } from "@angular/router";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { LoadingModalComponent } from "./components/loading-modal/loading-modal.component";
import { MatDialogModule } from "@angular/material/dialog";

@NgModule({
    declarations: [NavbarComponent, LoadingModalComponent],
    imports: [CommonModule, 
        MatSidenavModule, 
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        RouterModule,
        MatProgressSpinnerModule,
        MatDialogModule
    ],
    exports: [NavbarComponent]
})
export class SharedModule { }
