import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProdottiService, Prodotto } from '../prodotti-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-aggiungi-prodotto',
  standalone: true,
  imports: [
    MatFormFieldModule, MatInputModule, MatIconModule,
    MatDividerModule, MatButtonModule, ReactiveFormsModule
  ],
  templateUrl: './aggiungi-prodotto.component.html',
  styleUrls: ['./aggiungi-prodotto.component.css'],
})
export class AggiungiProdottoComponent {
  // Form per il prodotto
  prodottoForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private prodottiService: ProdottiService,
    private router: Router
  ) {
    // Inizializziamo i campi del form con le validazioni
    this.prodottoForm = this.fb.group({
      nome: ['', Validators.required],
      descrizione: ['', Validators.required],
      prezzo: [null, [Validators.required, Validators.min(0.01)]],
      categoriaId: [null, Validators.required],
    });
  }

  // Funzione per aggiungere un prodotto via servizio
  aggiungiProdotto(): void {
    if (this.prodottoForm.valid) {
      const prodotto: Prodotto = this.prodottoForm.value;
      this.prodottiService.aggiungiProdotto(prodotto).subscribe({
        next: () => {
          alert('Prodotto aggiunto con successo!');
          // Torniamo alla lista dei prodotti
          this.router.navigate(['/visualizza-catalogo']);
        },
        error: (err) => {
          console.error('Errore durante l\'aggiunta del prodotto:', err);
          alert('Errore durante l\'aggiunta del prodotto. Riprova.');
        },
      });
    } else {
      alert('Compila tutti i campi del modulo.');
    }
  }
}
