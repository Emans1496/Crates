import { Component } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'sidenav-drawer-overview-example',
  templateUrl: './basic-drawer.component.html',
  styleUrls: ['./basic-drawer.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatSidenavModule,
    MatIconModule,
    MatDividerModule,
    MatButtonModule,
    RouterModule,
    RouterOutlet
  ]
})
export class SidenavDrawerOverviewExample {
  /*
    Questo componente è “standalone” (niente import da un modulo).
    Al suo interno abbiamo importato tutto ciò che ci serve:
    - moduli di Material (Sidenav, Icon, Divider, Button)
    - RouterModule e RouterOutlet per la navigazione
    - CommonModule per le direttive base Angular (ngIf, ngFor, ecc.)
    Nella template, abbiamo una sidebar (drawer) e un content principale.
  */
}
