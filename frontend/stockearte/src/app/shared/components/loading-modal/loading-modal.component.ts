import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-loading-modal',
  templateUrl: './loading-modal.component.html',
  styleUrl: './loading-modal.component.css'
})
export class LoadingModalComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: { message: string }) {}
}
