import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProdottiService, Prodotto } from '../prodotti-service.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-modifica-prodotto',
  standalone: true,
  imports: [
    MatFormField, MatLabel, ReactiveFormsModule, MatFormFieldModule,
    MatInputModule, MatIconModule, MatDividerModule, MatButtonModule
  ],
  templateUrl: './modifica-prodotto.component.html',
  styleUrls: ['./modifica-prodotto.component.css'],
})
export class ModificaProdottoComponent implements OnInit {
  prodottoForm: FormGroup;
  prodottoId!: number; // per conservare l’ID del prodotto

  constructor(
    private fb: FormBuilder,
    private prodottiService: ProdottiService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    // Inizializziamo il form con i campi richiesti
    this.prodottoForm = this.fb.group({
      nome: ['', Validators.required],
      descrizione: ['', Validators.required],
      prezzo: [null, [Validators.required, Validators.min(0.01)]],
      categoriaId: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    // Prendiamo l’ID del prodotto dalla rotta
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.prodottoId = +id;
      // Recuperiamo i dettagli del prodotto da modificare
      this.prodottiService.getProdottoById(this.prodottoId).subscribe({
        next: (prodotto) => {
          if (prodotto) {
            // Patchiamo (popoliamo) il form con i dati recuperati
            this.prodottoForm.patchValue(prodotto);
          }
        },
        error: (err) => {
          console.error('Errore durante il recupero del prodotto:', err);
        },
      });
    }
  }

  // Al submit, se il form è valido, salviamo le modifiche
  modificaProdotto(): void {
    if (this.prodottoForm.valid) {
      // Creiamo un oggetto Prodotto con l'ID e i campi del form
      const prodotto: Prodotto = { id: this.prodottoId, ...this.prodottoForm.value };
      this.prodottiService.modificaProdotto(prodotto).subscribe({
        next: () => {
          alert('Prodotto modificato con successo!');
          // Torniamo alla lista prodotti
          this.router.navigate(['/visualizza-catalogo']);
        },
        error: (err) => {
          console.error('Errore durante la modifica del prodotto:', err);
          alert('Errore durante la modifica del prodotto. Riprova.');
        },
      });
    } else {
      alert('Compila tutti i campi del modulo.');
    }
  }
}
