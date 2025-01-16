import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CategorieService, Categoria } from '../categorie-service.service';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-modifica-categoria',
  standalone: true,
  imports: [
    ReactiveFormsModule, MatFormField, MatLabel, MatInputModule,
    MatFormFieldModule, MatIconModule, MatDividerModule, MatButtonModule
  ],
  templateUrl: './modifica-categoria.component.html',
  styleUrls: ['./modifica-categoria.component.css'],
})
export class ModificaCategoriaComponent implements OnInit {
  categoriaForm: FormGroup;
  categoriaId!: number; // ID della categoria

  constructor(
    private fb: FormBuilder,
    private categorieService: CategorieService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    // Inizializziamo il form con la validazione sul nomeCategoria
    this.categoriaForm = this.fb.group({
      nomeCategoria: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    // Recupera l'ID della categoria dalla rotta (parametro :id)
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.categoriaId = +id; // converte in numero
      // Chiediamo i dettagli al servizio
      this.categorieService.getCategorieById(this.categoriaId).subscribe({
        next: (categoria) => {
          if (categoria) {
            // Impostiamo i valori nel form
            this.categoriaForm.patchValue(categoria);
          }
        },
        error: (err) => {
          console.error('Errore durante il recupero della categoria:', err);
        },
      });
    }
  }

  // Quando clicchiamo "Salva", se il form è valido inviamo i dati
  modificaCategoria(): void {
    if (this.categoriaForm.valid) {
      const categoria: Categoria = { id: this.categoriaId, ...this.categoriaForm.value };
      this.categorieService.modificaCategoria(categoria).subscribe({
        next: () => {
          alert('Categoria modificata con successo!');
          this.router.navigate(['/visualizza-categorie']);
        },
        error: (err) => {
          console.error('Errore durante la modifica della categoria:', err);
          alert('Errore durante la modifica. Riprova.');
        },
      });
    } else {
      alert('Compila tutti i campi del modulo.');
    }
  }
}
