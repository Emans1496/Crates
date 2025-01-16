import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSort } from '@angular/material/sort';
import { ProdottiService, Prodotto } from '../prodotti-service.service';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatBottomSheet, MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { BottomSheetMenuComponent } from '../bottom-sheet-menu/bottom-sheet-menu.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-visualizza-catalogo',
  standalone: true,
  imports: [
    MatSort, MatTableModule, MatFormFieldModule,
    MatInputModule, MatSortModule, MatBottomSheetModule,
    MatSidenavModule, MatPaginator
  ],
  templateUrl: './visualizza-catalogo-guest.component.html',
  styleUrls: ['./visualizza-catalogo-guest.component.css'],
})
export class VisualizzaCatalogoGuestComponent implements OnInit {
  // Definiamo quali colonne mostrare: qui non abbiamo la colonna “actions”
  displayedColumns: string[] = ['id', 'nome', 'descrizione', 'prezzo', 'categoriaId'];
  dataSource = new MatTableDataSource<Prodotto>();

  @ViewChild(MatSort) sort!: MatSort;

  constructor(private prodottiService: ProdottiService, private bottomSheet: MatBottomSheet) {}

  ngOnInit(): void {
    // Carica i dati dal servizio (lista di prodotti)
    this.prodottiService.getProdotti().subscribe({
      next: (data) => {
        this.dataSource.data = data; 
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.error('Errore durante il caricamento dei prodotti:', err);
      },
    });
  }

  // Filtra i risultati della tabella
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  // Questa funzione esiste, ma per la versione “guest” probabilmente non la usiamo
  // Potresti anche eliminarla se non serve mostrare un bottomSheet.
  openBottomSheet(row: Prodotto): void {
    this.bottomSheet.open(BottomSheetMenuComponent, {
      data: { id: row.id },
    });
  }
}
