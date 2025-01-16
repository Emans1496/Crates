import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidenavDrawerOverviewExample } from './basic-drawer/basic-drawer.component';

// Il decoratore @Component ci permette di definire un componente Angular.
// In questa definizione specifichiamo:
// - selector: il nome del tag HTML con cui utilizzeremo questo componente (app-root).
// - imports: definisce quali altre risorse/feature Angular vogliamo importare, ad esempio il RouterOutlet.
// - templateUrl e styleUrl puntano ai file HTML e CSS associati al nostro componente.
@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  // title è una semplice variabile di classe che possiamo usare all’interno del template
  // e che definisce il titolo dell’applicazione.
  title = 'Crates';
}
