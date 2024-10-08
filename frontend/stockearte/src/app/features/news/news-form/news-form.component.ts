import { Component, Inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.css']
})
export class NewsFormComponent {
  selectionForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<NewsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.selectionForm = this.fb.group({
      selections: this.fb.array([])
    });
    this.initializeForm();
  }

  initializeForm(): void {
    const tallesColores = this.data.tallesColores;
    tallesColores.forEach((talleColor: { talle: string; colores: string[]; }) => {
      this.addSelection(talleColor.talle, talleColor.colores);
    });
  }

  get selections(): FormArray {
    return this.selectionForm.get('selections') as FormArray;
  }

  addSelection(talle: string, colores: string[]): void {
    colores.forEach(color => {
      this.selections.push(
        this.fb.group({
          talle: [talle],
          color: [color],
          cantidad: [0, [Validators.required, Validators.min(0)]]
        })
      );
    });
  }

  saveSelections(): void {
    if (this.selectionForm.valid) {
      this.dialogRef.close(this.selectionForm.value);
    }
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
