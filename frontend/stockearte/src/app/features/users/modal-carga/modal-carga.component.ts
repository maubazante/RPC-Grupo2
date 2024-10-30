import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-carga',
  templateUrl: './modal-carga.component.html',
  styleUrl: './modal-carga.component.css'
})
export class ModalCargaComponent {
  response: string[]
  huboRepetidos: boolean = false;

  constructor(public dialogRef: MatDialogRef<ModalCargaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { response: string[] }
  ) {
    this.data ? this.response = this.data.response : this.response = ["No hubo información"]
    this.data.response.some((line: string) => line.includes('duplicado.')) ? this.huboRepetidos = true : this.huboRepetidos = false;
  }

}
