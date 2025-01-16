import { Routes } from '@angular/router';
import { VisualizzaCatalogoComponent } from './visualizza-catalogo/visualizza-catalogo.component';
import { VisualizzaCategorieComponent } from './visualizza-categorie/visualizza-categorie.component';
import { VisualizzaCatalogoGuestComponent } from './visualizza-catalogo-guest/visualizza-catalogo-guest.component';
import { AggiungiProdottoComponent } from './aggiungi-prodotto/aggiungi-prodotto.component';
import { AggiungiCategoriaComponent } from './aggiungi-categoria/aggiungi-categoria.component';
import { ModificaProdottoComponent } from './modifica-prodotto/modifica-prodotto.component';
import { ModificaCategoriaComponent } from './modifica-categoria/modifica-categoria.component';
import { EliminaProdottoComponent } from './elimina-prodotto/elimina-prodotto.component';
import { EliminaCategoriaComponent } from './elimina-categoria/elimina-categoria.component';
import { HomeComponent } from './home/home.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { SidenavDrawerOverviewExample } from './basic-drawer/basic-drawer.component';
import { AuthGuard } from './auth/auth.guard'; 

// Definiamo un array di Routes che mappa path (URL) con i componenti Angular da mostrare.
export const routes: Routes = [
  // Rotta di default: mostriamo la pagina di login. È "pubblica", quindi non usa la sidebar.
  { path: '', component: LoginPageComponent },

  // Rotta "genitore" protetta, che include la sidebar.
  // Al suo interno ci sono le rotte figlie che useranno lo stesso layout (la sidebar).
  {
    path: '',
    component: SidenavDrawerOverviewExample,
    canActivate: [AuthGuard],  // Possiamo bloccare questa rotta e le figlie se l’utente non è autenticato
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'visualizza-catalogo', component: VisualizzaCatalogoComponent },
      { path: 'visualizza-categorie', component: VisualizzaCategorieComponent },
      { path: 'aggiungi-prodotto', component: AggiungiProdottoComponent },
      { path: 'aggiungi-categoria', component: AggiungiCategoriaComponent },
      // Notiamo come passiamo l’ID nella rotta: /modifica-prodotto/:id
      { path: 'modifica-prodotto/:id', component: ModificaProdottoComponent },
      { path: 'modifica-categoria/:id', component: ModificaCategoriaComponent },
      { path: 'elimina-prodotto/:id', component: EliminaProdottoComponent },
      { path: 'elimina-categoria/:id', component: EliminaCategoriaComponent },
    ],
  },

  // Rotta per vedere il catalogo in modalità guest (senza login).
  { path: 'visualizza-catalogo-guest', component: VisualizzaCatalogoGuestComponent },

  // Fallback nel caso l’utente inserisca una rotta non definita: rimanda alla home ('').
  { path: '**', redirectTo: '' },
];
