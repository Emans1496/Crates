import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient } from '@angular/common/http';

// Qui definiamo un oggetto di tipo ApplicationConfig. È un modo per raggruppare
// tutte le configurazioni della nostra app (routing, animazioni, http, ecc.) in un unico posto.
export const appConfig: ApplicationConfig = {
  providers: [
    // Attiviamo la rilevazione del cambiamento (change detection) basata su Zone
    // con eventCoalescing impostato a true (per ottimizzare gli eventi).
    provideZoneChangeDetection({ eventCoalescing: true }),

    // Forniamo all'applicazione il router, passando le nostre routes.
    provideRouter(routes),

    // Abilitiamo le animazioni in modo asincrono (utile se usiamo Angular Material o simili).
    provideAnimationsAsync(),

    // Iniettiamo il servizio HttpClient in tutta l’app, così da poter effettuare chiamate HTTP.
    provideHttpClient(),
  ]
};
