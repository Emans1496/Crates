import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CatalogInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    const catalogId = '12345'; // Puoi modificarlo dinamicamente
    console.log('Interceptor chiamato per la richiesta:', req.url);
    const clonedRequest = req.clone({
      setHeaders: {
        'Catalog-Id': catalogId,
      },
    });

    console.log('Interceptor aggiunge Catalog-Id:', clonedRequest.headers.get('Catalog-Id'));

    return next.handle(clonedRequest);
  }
}
