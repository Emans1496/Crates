import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CategorieService } from '../categorie-service.service';
import { ConfirmDialogComponent } from '../confirm-dialog-component/confirm-dialog-component.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-elimina-categoria',
  standalone: true,
  imports: [],
  template: '',
  styleUrls: ['./elimina-categoria.component.css'],
})
export class EliminaCategoriaComponent {
  /*
    Questo componente è "vuoto" nel template perché agisce programmaticamente.
    Potresti usarlo come una pagina a sé stante oppure come una funzione di servizio
    che, prima di eliminare, mostra un dialog di conferma.
  */

  constructor(
    private categorieService: CategorieService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  // Funzione per eliminare una categoria con conferma
  eliminaCategoria(id: number): void {
    // Apri un dialog di conferma
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Sei sicuro di voler eliminare questa categoria?' },
    });

    // Quando il dialog si chiude, controlliamo il risultato
    dialogRef.afterClosed().subscribe((result) => {
      if (result === true) {
        // Se confermato, chiamiamo il servizio per eliminare la categoria
        this.categorieService.eliminaCategoria(id).subscribe({
          next: () => {
            // Torniamo alla pagina di visualizzazione
            this.router.navigate(['/visualizza-categorie']);
          },
          error: (err) => {
            console.error('Errore durante l\'eliminazione della categoria:', err);
            alert('Errore durante l\'eliminazione della categoria.');
          },
        });
      }
    });
  }
}
