import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CategorieService } from '../categorie-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-aggiungi-categoria',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './aggiungi-categoria.component.html',
  styleUrls: ['./aggiungi-categoria.component.css'],
})
export class AggiungiCategoriaComponent {
  // FormGroup per la nuova categoria
  categoriaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private categorieService: CategorieService,
    private router: Router
  ) {
    // Inizializza il form: nomeCategoria è obbligatorio
    this.categoriaForm = this.fb.group({
      nomeCategoria: ['', Validators.required],
    });
  }

  aggiungiCategoria(): void {
    if (this.categoriaForm.valid) {
      // Recupera i dati dal form
      const categoria = this.categoriaForm.value;
      // Chiamata al servizio per aggiungere la categoria
      this.categorieService.aggiungiCategoria(categoria).subscribe({
        next: () => {
          alert('Categoria aggiunta con successo!');
          // Reindirizza alla pagina di visualizzazione
          this.router.navigate(['/visualizza-categorie']);
        },
        error: (err) => {
          console.error('Errore durante l\'aggiunta della categoria:', err);
          alert('Errore durante l\'aggiunta della categoria. Riprova.');
        },
      });
    } else {
      alert('Compila tutti i campi del modulo.');
    }
  }
}
