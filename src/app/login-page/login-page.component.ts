import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Importa FormsModule per utilizzare ngModel (two-way binding)
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [FormsModule, RouterModule], 
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  // Queste due proprietà andranno ad associarsi ai campi input del form (email e password).
  email: string = '';
  password: string = '';

  // Per poter navigare programmaticamente tra le rotte, iniettiamo il Router nel costruttore.
  constructor(private router: Router) {}

  // Metodo chiamato dal form al (ngSubmit). Esegue un controllo banale su email e password:
  // se corrispondono a 'admin@crates.com' e 'password', setta isLoggedIn = true e fa un navigate su /home.
  onSubmit() {
    if (this.email === 'admin@crates.com' && this.password === 'password') {
      localStorage.setItem('isLoggedIn', 'true');
      this.router.navigate(['/home']); // Cambia la rotta
    } else {
      alert('Credenziali non valide');
    }
  }
}
