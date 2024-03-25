import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { ApiError } from '../erro-api';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-error-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule, MatListModule],
  template: `
    <h1 mat-dialog-title style="color: red;">Error!</h1>
    <div mat-dialog-content>{{ error.message }}</div>
    <mat-list role="list">
      <div *ngFor="let err of error.erros">
        <mat-list-item role="erroList">{{err.message}}</mat-list-item>
      </div>
    </mat-list>
    <div mat-dialog-actions align="center">
      <button mat-stroked-button mat-dialog-close>Close</button>
    </div>
  `
})
export class ErrorDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public error: ApiError = { message: 'Erro ao realizar a operação', erros: [] }) { }
}
