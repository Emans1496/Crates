import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interfaccia che definisce la struttura di una "Categoria":
// - id: numero identificativo
// - nomeCategoria: stringa che rappresenta il nome
export interface Categoria {
  id: number;
  nomeCategoria: string;
}

// Il decoratore @Injectable indica che questa classe può essere iniettata come servizio in Angular.
@Injectable({
  providedIn: 'root', // Significa che è disponibile a livello di root dell’app.
})
export class CategorieService {
  // baseUrl è l’indirizzo base del nostro server backend.
  private baseUrl = 'http://localhost:8080/Crates'; // URL del server backend

  // Il costruttore riceve un oggetto HttpClient che useremo per fare chiamate HTTP.
  constructor(private http: HttpClient) {}

  // Metodo per ottenere tutte le categorie dal backend, ritorna un Observable di un array di Categoria.
  getCategorie(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.baseUrl}/VisualizzaCategorieServlet`);
  }

  // Metodo per ottenere una categoria specifica in base al suo ID.
  getCategorieById(id: number): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.baseUrl}/VisualizzaCategoriaByIdServlet?id=${id}`);
  }

  // Metodo per aggiungere una nuova categoria.
  // Riceve un oggetto con nomeCategoria e lo invia in POST al backend.
  aggiungiCategoria(categoria: { nomeCategoria: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/AggiungiCategoriaServlet`, categoria, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  // Metodo per modificare una categoria già esistente, prendendo la Categoria come parametro.
  modificaCategoria(categoria: Categoria): Observable<any> {
    return this.http.post(`${this.baseUrl}/ModificaCategoriaServlet`, categoria, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  // Metodo per eliminare una categoria esistente in base all’ID.
  eliminaCategoria(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/EliminaCategoriaServlet?id=${id}`);
  }
}
