import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatOptionModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatTooltipModule } from "@angular/material/tooltip";
import { OrderFormComponent } from "./order-form/order-form.component";
import { OrderListComponent } from "./order-list/order-list.component";
import { OrdersRoutingModule } from "./orders-routing.module";
import { MatDividerModule } from "@angular/material/divider";


@NgModule({
    declarations: [OrderListComponent, OrderFormComponent],
    imports: [
      CommonModule,
      MatTableModule,
      MatButtonModule,
      MatToolbarModule,
      MatIconModule,
      MatDialogModule,
      MatFormFieldModule,
      ReactiveFormsModule,
      MatOptionModule,
      MatSelectModule,
      MatInputModule,
      MatTooltipModule,
      MatIconModule,
      OrdersRoutingModule,
      MatDividerModule
    ],
    exports: [OrderListComponent, OrderFormComponent]
  })
  export class OrdersModule { }
  