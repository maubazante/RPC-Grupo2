import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { ReportsFormComponent } from '../reports-form/reports-form.component';
import { ReportsService } from '../../../core/services/reports.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-reports-list',
  templateUrl: './reports-list.component.html',
  styleUrls: ['./reports-list.component.css']
})
export class ReportsListComponent implements OnInit {
  dataSource: any[] = [];
  searchTerm: string = '';
  displayedColumns: string[] = ['id', 'codigoArticulo', 'tienda', 'fechaSolicitud', 'estado'];

  constructor(private reportsService: ReportsService, private dialog: MatDialog, public authService: AuthService) {}

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.reportsService.getReports().subscribe((data: any) => {
      console.log(data);
      this.dataSource = data;
    });
  }

  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchTerm = input.value;
    this.applyFilter();
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.applyFilter();
  }

  applyFilter(): void {
  }

  openFilterModal(): void {
    this.dialog.open(ReportsFormComponent);
  }
}
