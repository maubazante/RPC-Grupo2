import { Injectable } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LoadingModalComponent } from '../../shared/components/loading-modal/loading-modal.component';

@Injectable({
  providedIn: 'root'
})
export class LoadingModalService {
  private dialogRef: MatDialogRef<LoadingModalComponent> | null = null;

  constructor(private dialog: MatDialog) {}

  showLoading(message: string = 'Cargando...') {
    if (!this.dialogRef) {
      this.dialogRef = this.dialog.open(LoadingModalComponent, {
        data: { message },
        disableClose: true
      });
    }
  }

  hideLoading() {
    if (this.dialogRef) {
      this.dialogRef.close();
      this.dialogRef = null;
    }
  }
}
