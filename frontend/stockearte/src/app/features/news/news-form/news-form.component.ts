import { Component, Inject } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-news-form',
  templateUrl: './news-form.component.html',
  styleUrls: ['./news-form.component.css']
})
export class NewsFormComponent {
  newsForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<NewsFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { newsItem: { tallesColores: { talle: string, colores: string[] }[] }, action: string }
  ) {
    this.newsForm = this.fb.group({
      tallesColores: this.fb.array(this.buildTallesColores())
    });
  }

  buildTallesColores(): FormGroup[] {
    const formGroups: FormGroup[] = [];

    this.data.newsItem.tallesColores.forEach(talleProducto => {
      const talle = talleProducto.talle;
      talleProducto.colores.forEach(color => {
        formGroups.push(
          this.fb.group({
            talle: [talle],             
            color: [color],             
            selected: new FormControl(false)  
          })
        );
      });
    });

    return formGroups;
  }

  get tallesColoresArray(): FormArray {
    return this.newsForm.get('tallesColores') as FormArray;
  }

  onSave(): void {
    const selectedData = this.tallesColoresArray.value
      .filter((combo: any) => combo.selected)
      .map((combo: any) => ({ talle: combo.talle, color: combo.color }));

    this.dialogRef.close(selectedData);
  }

  onClose(): void {
    this.dialogRef.close();
  }
}
