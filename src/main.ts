import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CatalogInterceptor } from './app/catalog-interceptor.interceptor';

// bootstrapApplication è il nuovo metodo (dalla versione 15 di Angular in poi) per far partire l’app.
// Al suo interno passiamo il componente principale (AppComponent) e tutte le configurazioni aggiuntive.
bootstrapApplication(AppComponent, {
  // Copiamo tutto ciò che c’è in appConfig (routing, animazioni, http, ecc.)
  ...appConfig,

  // Poi aggiungiamo i nostri interceptor, se ne abbiamo di specifici.
  providers: [
    // Espandiamo (spread operator ...) i providers esistenti...
    ...(appConfig.providers || []),

    // ...e aggiungiamo il CatalogInterceptor, che intercetta le richieste HTTP
    // per fare qualcosa (log, aggiunta header, gestione errori, ecc.).
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CatalogInterceptor,
      multi: true
    }
  ]
})
.catch((err) => console.error(err)); // Se c’è qualche errore in bootstrap, lo stampiamo in console.
