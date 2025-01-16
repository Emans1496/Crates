import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interfaccia per definire la struttura di un "Prodotto".
export interface Prodotto {
  id: number;
  nome: string;
  descrizione: string;
  prezzo: number;
  categoriaId: number; // Indica a quale categoria appartiene
}

@Injectable({
  providedIn: 'root',
})
export class ProdottiService {
  // Indirizzo base del server
  private baseUrl = 'http://localhost:8080/Crates'; // URL del server backend

  constructor(private http: HttpClient) {}

  // Metodo per ottenere tutti i prodotti. Ritorna un array di Prodotto.
  getProdotti(): Observable<Prodotto[]> {
    return this.http.get<Prodotto[]>(
      `${this.baseUrl}/VisualizzaProdottiServlet`
    );
  }

  // Metodo per aggiungere un nuovo prodotto.
  // Inviamo in POST un oggetto di tipo Prodotto al backend.
  aggiungiProdotto(prodotto: Prodotto): Observable<any> {
    return this.http.post(
      'http://localhost:8080/Crates/AggiungiProdottoServlet',
      prodotto,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  // Metodo per ottenere un singolo prodotto tramite ID.
  getProdottoById(id: number): Observable<Prodotto> {
    return this.http.get<Prodotto>(
      `http://localhost:8080/Crates/VisualizzaProdottoByIdServlet?id=${id}`
    );
  }

  // Metodo per modificare un prodotto già esistente.
  modificaProdotto(prodotto: Prodotto): Observable<any> {
    return this.http.post(`${this.baseUrl}/ModificaProdottoServlet`, prodotto, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  // Metodo per eliminare un prodotto specifico, in base all’ID.
  eliminaProdotto(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/Crates/EliminaProdottoServlet?id=${id}`);
  }
}
