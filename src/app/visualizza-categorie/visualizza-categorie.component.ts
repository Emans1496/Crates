import { Component, OnInit, ViewChild } from '@angular/core';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { CategorieService, Categoria } from '../categorie-service.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSortModule } from '@angular/material/sort';
import { MatIcon } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BottomSheetMenuCategoriaComponent } from '../bottom-sheet-menu-categoria-component/bottom-sheet-menu-categoria-component.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-visualizza-categorie',
  standalone: true,
  imports: [
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatSortModule,
    MatIcon,
    MatPaginatorModule,
    MatSidenavModule,
    MatButtonModule,
    MatDividerModule,
  ],
  templateUrl: './visualizza-categorie.component.html',
  styleUrls: ['./visualizza-categorie.component.css'],
})
export class VisualizzaCategorieComponent implements OnInit {
  // Colonne della tabella
  displayedColumns: string[] = ['id', 'nomeCategoria', 'actions'];
  dataSource = new MatTableDataSource<Categoria>();

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private categorieService: CategorieService, private bottomSheet: MatBottomSheet) {}

  ngOnInit(): void {
    // Al caricamento, recuperiamo le categorie dal servizio
    this.categorieService.getCategorie().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      },
      error: (err) => console.error('Errore durante il caricamento delle categorie:', err),
    });
  }

  // Filtra i dati della tabella
  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }

  // Apre il bottom sheet per eventuali azioni (modifica o elimina) di una categoria
  openBottomSheet(row: Categoria): void {
    this.bottomSheet.open(BottomSheetMenuCategoriaComponent, {
      data: { id: row.id },
    });
  }
}
