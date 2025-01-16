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
import { MatIcon } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-visualizza-catalogo',
  standalone: true,
  imports: [
    MatSort, MatTableModule, MatFormFieldModule, MatInputModule, MatSortModule,
    MatBottomSheetModule, MatSidenavModule, MatIcon, MatPaginator, MatButtonModule,
    MatDividerModule,
  ],
  templateUrl: './visualizza-catalogo.component.html',
  styleUrls: ['./visualizza-catalogo.component.css'],
})
export class VisualizzaCatalogoComponent implements OnInit {
  // Definiamo le colonne della tabella
  displayedColumns: string[] = ['id', 'nome', 'descrizione', 'prezzo', 'categoriaId', 'actions'];

  // DataSource su cui mat-table fa binding. Nel ngOnInit carichiamo i dati da ProdottiService.
  dataSource = new MatTableDataSource<Prodotto>();

  // ViewChild per ottenere un riferimento a MatSort (ordinamento)
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private prodottiService: ProdottiService, private bottomSheet: MatBottomSheet) {}

  // Al caricamento del componente:
  ngOnInit(): void {
    // Chiamiamo il servizio per recuperare i prodotti
    this.prodottiService.getProdotti().subscribe({
      next: (data) => {
        // Mettiamo i prodotti nel dataSource
        this.dataSource.data = data;
        // Attiviamo il sorting
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.error('Errore durante il caricamento dei prodotti:', err);
      },
    });
  }

  // Metodo per applicare il filtro di ricerca nella tabella
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    // Il datasource filtra i dati usando la stringa in filterValue
    this.dataSource.filter = filterValue;
  }

  // Quando l’utente clicca sull’icona "edit" (mat-mini-fab), apriamo il bottom sheet
  openBottomSheet(row: Prodotto): void {
    this.bottomSheet.open(BottomSheetMenuComponent, {
      // Passiamo l’ID del prodotto come data
      data: { id: row.id },
    });
  }
}
