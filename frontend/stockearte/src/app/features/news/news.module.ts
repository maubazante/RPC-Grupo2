import { NgModule } from "@angular/core";
import { NewsListComponent } from "./news-list/news-list.component";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatOptionModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatTooltipModule } from "@angular/material/tooltip";
import { NewsRoutingModule } from "./news-routing.module";
import { NewsFormComponent } from "./news-form/news-form.component";
import { MatCheckboxModule } from "@angular/material/checkbox";

@NgModule({
    declarations: [NewsListComponent, NewsFormComponent],
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
      NewsRoutingModule,
      MatCheckboxModule,
      MatDividerModule
    ],
    exports: [NewsListComponent, NewsFormComponent]
  })
  export class NewsModule { }
  