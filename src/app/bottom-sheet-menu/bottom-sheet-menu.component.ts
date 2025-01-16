import { Component, Inject } from '@angular/core';
import { MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ProdottiService } from '../prodotti-service.service';
import { ConfirmDialogComponent } from '../confirm-dialog-component/confirm-dialog-component.component';

@Component({
  selector: 'app-bottom-sheet-menu',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogModule],
  template: `
    <div>
      <h3>Azioni</h3>
      <button mat-button (click)="navigateToModifica()">Modifica Prodotto</button>
      <button mat-button style="color: red;" (click)="confirmElimina()">Elimina Prodotto</button>
      <button mat-button (click)="chiudi()">Chiudi</button>
    </div>
  `,
  styles: [`
    div {
      display: flex;
      flex-direction: column;
    }
    button {
      margin: 5px 0;
    }
  `]
})
export class BottomSheetMenuComponent {
  /*
    Questo componente appare come un "bottom sheet" (il classico pannello
    che scorre dal basso) quando clicchiamo l'icona nella tabella prodotti.
    Passiamo i dati (id del prodotto) usando MAT_BOTTOM_SHEET_DATA.
  */

  constructor(
    private bottomSheetRef: MatBottomSheetRef<BottomSheetMenuComponent>,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: { id: number },
    private prodottiService: ProdottiService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  // Naviga alla pagina di modifica del prodotto (usando l’ID passato)
  navigateToModifica(): void {
    this.bottomSheetRef.dismiss();
    setTimeout(() => {
      // Ritardo di 200ms per dare il tempo di chiudere l’animazione del bottom sheet
      this.router.navigate(['/modifica-prodotto', this.data.id]);
    }, 200);
  }

  // Conferma l’eliminazione del prodotto aprendo una finestra di dialogo di conferma
  confirmElimina(): void {
    this.bottomSheetRef.dismiss();
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Sei sicuro di voler eliminare questo prodotto?' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      // Se l’utente conferma (result === true), chiamiamo il servizio per eliminare il prodotto.
      if (result === true) {
        this.prodottiService.eliminaProdotto(this.data.id).subscribe({
          next: () => console.log('Prodotto eliminato con successo'),
          error: (err) => console.error('Errore durante l\'eliminazione:', err),
        });
      }
    });
  }

  // Chiudi il bottom sheet senza fare nulla
  chiudi(): void {
    this.bottomSheetRef.dismiss();
  }
}
