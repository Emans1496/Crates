import { Component, Inject } from '@angular/core';
import { MatBottomSheetRef, MAT_BOTTOM_SHEET_DATA } from '@angular/material/bottom-sheet';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CategorieService } from '../categorie-service.service';
import { ConfirmDialogComponent } from '../confirm-dialog-component/confirm-dialog-component.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-bottom-sheet-menu-categoria',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatDialogModule],
  template: `
    <div>
      <h3>Azioni</h3>
      <button mat-button (click)="navigateToModifica()">Modifica Categoria</button>
      <button mat-button style="color: red;" (click)="confirmElimina()">Elimina Categoria</button>
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
export class BottomSheetMenuCategoriaComponent {
  /*
    Stesso discorso di bottom-sheet, ma questa volta
    dedicato alle azioni su una categoria: modifica o elimina.
  */

  constructor(
    private bottomSheetRef: MatBottomSheetRef<BottomSheetMenuCategoriaComponent>,
    @Inject(MAT_BOTTOM_SHEET_DATA) public data: { id: number },
    private categorieService: CategorieService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  // Vai alla pagina di modifica per la categoria con l’ID passato
  navigateToModifica(): void {
    this.bottomSheetRef.dismiss();
    setTimeout(() => {
      this.router.navigate(['/modifica-categoria', this.data.id]);
    }, 200);
  }

  // Chiedi conferma dell’eliminazione, poi chiama il servizio se l’utente conferma
  confirmElimina(): void {
    this.bottomSheetRef.dismiss();
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Sei sicuro di voler eliminare questa categoria?' },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === true) {
        this.categorieService.eliminaCategoria(this.data.id).subscribe({
          next: () => {
            alert('Categoria eliminata con successo!');
            window.location.reload(); 
            // Per ricaricare la pagina e aggiornare la lista categorie
          },
          error: (err) => {
            console.error('Errore durante l\'eliminazione della categoria:', err);
            alert('Errore durante l\'eliminazione.');
          },
        });
      }
    });
  }

  // Chiude il bottom sheet
  chiudi(): void {
    this.bottomSheetRef.dismiss();
  }
}
