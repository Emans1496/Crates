import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogModule],
  template: `
    <h2 mat-dialog-title>Conferma</h2>
    <mat-dialog-content>{{ data.message }}</mat-dialog-content>
    <mat-dialog-actions>
      <button mat-button (click)="onNoClick()">Annulla</button>
      <button mat-button color="warn" (click)="onConfirm()">Conferma</button>
    </mat-dialog-actions>
  `,
})
export class ConfirmDialogComponent {
  /*
    Dialog di conferma (ad esempio, "Sei sicuro di voler eliminare?").
    Riceve la stringa "data.message" e mostra i pulsanti "Annulla" e "Conferma".
  */

  constructor(
    private router: Router,
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string }
  ) {}

  // Quando l'utente clicca su "Annulla", chiudiamo la finestra ritornando false
  onNoClick(): void {
    this.dialogRef.close(false);
  }

  // Quando l'utente clicca su "Conferma", chiudiamo la finestra ritornando true
  onConfirm(): void {
    this.dialogRef.close(true);

    /*
      Questo if controlla la rotta corrente:
      - se siamo in /visualizza-catalogo, ci spostiamo su /home, poi
        torniamo a /visualizza-catalogo (forse per forzare un refresh).
      - se siamo in /visualizza-categorie, facciamo un alert e
        ritorniamo a /visualizza-categorie con lo stesso trucchetto.
    */
    if (this.router.url === '/visualizza-catalogo') {
      this.router.navigate(['/home']).then(() => {
        this.router.navigate(['/visualizza-catalogo']);
      });
    } else if (this.router.url === '/visualizza-categorie') {
      alert('Categoria eliminata con successo!');
      this.router.navigate(['/home']).then(() => {
        this.router.navigate(['/visualizza-categorie']);
      });
    }
  }
}
