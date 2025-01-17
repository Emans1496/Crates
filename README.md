![Crates Logo](https://raw.githubusercontent.com/Emans1496/Crates/refs/heads/main/public/assets/img/crates-logo.png)

# Crates

Crates è una webapp che funge da gestionale di magazzino.  
L’obiettivo è quello di permettere l’aggiunta, la modifica, l’eliminazione e la visualizzazione di prodotti e categorie, sia da parte di un utente amministratore (che ha il pieno controllo) sia da parte di un utente “guest” (che può solo visualizzare).

Dal **frontend** sviluppato in **Angular** fino al **backend** in **Java** con **Servlet** e un database **MySQL**, in questa documentazione trovi ogni passaggio e ogni ragionamento dietro a questa struttura.

---

## Struttura del Progetto

Il progetto si compone principalmente di tre macro-parti:

1. **Frontend Angular**  
   - Componenti, servizi e routing.  
   - Gestione di form per le operazioni CRUD (Create, Read, Update, Delete).  
   - Protezione tramite *AuthGuard*.  
   - UI curata con *Angular Material*.  

2. **Backend Java**  
   - Servlet per ogni operazione (Aggiungi, Modifica, Elimina, Visualizza).  
   - Configurazioni CORS per permettere la comunicazione con Angular (localhost:4200).  
   - Utilizzo di **Gson** per serializzare e deserializzare gli oggetti JSON.  

3. **Database MySQL**  
   - Tabelle *categorie* e *prodotti* collegate da una *foreign key* (`categoria_id`).  
   - Script di creazione del database (*CratesCreationDB*).  
   - Classi CRUD per interagire con le tabelle.  

---

## 1. Il Frontend Angular

### 1.1 Struttura dei Componenti

- **AppComponent**  
  Entry point dell’applicazione. Da qui parte la configurazione di Angular, con un semplice template (`app.component.html`) e uno stile (`app.component.css`).

- **LoginPageComponent**  
  Pagina di login. Qui troviamo un form con due campi: email e password. Se l’utente è `admin@crates.com` con password `password`, salviamo in `localStorage` un flag `isLoggedIn = 'true'`.

- **HomeComponent**  
  Mostra la pagina iniziale, con quattro “card” principali (Visualizza, Modifica, Aggiungi, Elimina) e una mini descrizione su come funziona l’app.

- **SidenavDrawerOverviewExample** (basic-drawer)  
  È la componente che gestisce la *sidebar*. Ha un `mat-drawer` a sinistra con i pulsanti di navigazione per passare da **Home**, **Visualizza Catalogo**, **Aggiungi Prodotto**, **Visualizza Categorie** e **Aggiungi Categoria**.  
  Nel content c’è un `router-outlet` che mostra le pagine figlie a seconda del path.

- **VisualizzaCatalogoComponent / VisualizzaCatalogoGuestComponent**  
  - Tabelle con la lista dei prodotti (nella versione “guest” non c’è la colonna “azioni”).  
  - Si appoggiano ai dati restituiti dal servizio `ProdottiService`.  
  - Permettono di filtrare e ordinare i prodotti con `mat-table` e `matSort`, oltre ad avere un `mat-paginator`.

- **VisualizzaCategorieComponent**  
  Stessa logica di “Visualizza Catalogo”, ma dedicata alle categorie. Possibilità di filtrare e aprire un menù azioni.

- **AggiungiCategoriaComponent / AggiungiProdottoComponent**  
  - **Form reattivi** (*ReactiveFormsModule*), con i campi necessari per creare un prodotto o una categoria.  
  - Al clic su “Aggiungi”, vengono inviati i dati (in JSON) al backend, che li salva nel DB.

- **ModificaCategoriaComponent / ModificaProdottoComponent**  
  - Al `ngOnInit` recuperano l’ID dalla route (`/modifica-categoria/:id` e `/modifica-prodotto/:id`).  
  - Chiedono i dati attuali al backend, li caricano nel form, permettono la modifica e al submit inviano la `POST` di aggiornamento.

- **EliminaCategoriaComponent / EliminaProdottoComponent**  
  - Non mostrano un template vero e proprio (spesso sono componenti di servizio o vengono richiamati via *bottom-sheet*).  
  - In alcuni casi, è implementata la logica di apertura di una finestra di conferma (dialog) e poi la chiamata di eliminazione.

- **BottomSheetMenuComponent / BottomSheetMenuCategoriaComponent**  
  - Piccola “mini finestra” che scivola dal basso. Quando clicchiamo su un’icona (p.es. `edit`) in una tabella, appare un menù con le azioni “Modifica” e “Elimina”.  
  - In caso di “Elimina”, viene aperto un **ConfirmDialogComponent** (dialog di conferma).

- **ConfirmDialogComponent**  
  - Mostra un popup con un messaggio tipo *“Sei sicuro di voler eliminare questo prodotto/categoria?”*, con due pulsanti “Annulla” e “Conferma”.  
  - Se l’utente conferma, si procede all’eliminazione effettiva.

### 1.2 Routing e Protezione

In `app.routes.ts` vediamo la configurazione delle rotte: `''` (login) e poi `SidenavDrawerOverviewExample` come “contenitore” di rotta protetta.

- **AuthGuard**: classe che implementa `CanActivate`. Controlla se `localStorage.getItem('isLoggedIn') === 'true'`. Se non è vero, reindirizza a `/login`.

### 1.3 Servizi Angular

- **ProdottiService / CategorieService**  
  - Definiscono i metodi `getProdotti()`, `aggiungiProdotto()`, `modificaProdotto()`, `eliminaProdotto()`, e analoghi per le categorie.  
  - Internamente usano `HttpClient` per chiamare le servlet sul backend.  
  - Restituiscono *Observable* di oggetti tipizzati (`Prodotto`, `Categoria`).

---

## 2. Il Backend Java (Servlet)

Il backend è costituito da un set di servlet, ciascuna esposta con una determinata route, che si occupa di eseguire un’operazione CRUD. Questi endpoint sono contattati dal frontend con richieste HTTP e scambiano dati in JSON (serializzato/deserializzato con *Gson*).

### 2.1 Configurazione CORS

In ogni servlet, nelle prime righe dei metodi (`POST`, `GET`, ecc.) troviamo:

```java
response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
```

Questo permette ad Angular (in esecuzione su localhost:4200) di poter fare chiamate al server Java (su un’altra porta), altrimenti i browser bloccherebbero la richiesta a causa di policy di sicurezza (CORS). Spesso vediamo anche doOptions implementato per gestire il “preflight” delle richieste.


### 2.2 Servlet Principali
- ** AggiungiCategoriaServlet / AggiungiProdottoServlet

Fanno una doPost: leggono il JSON dal body della richiesta (request.getReader()), lo convertono in un oggetto Categorie / Prodotti con Gson, e chiamano categorieCRUD.addCategoria() o prodottiCRUD.addProdotto().
Se tutto va bene, restituiscono un JSON {"success": "..."}, altrimenti un errore con status code appropriato.
EliminaCategoriaServlet / EliminaProdottoServlet

Possono essere implementati su doPost o su doDelete (oppure entrambi).
Leggono il parametro id dalla query string (request.getParameter("id")), lo convertono in int, e chiamano il metodo deleteCategoria(id) o deleteProdotto(id).
Analogamente, impostano la risposta JSON a seconda che l’eliminazione riesca o meno.
ModificaCategoriaServlet / ModificaProdottoServlet

Ricevono un JSON in POST, estraggono i campi (tra cui l’id e i valori da modificare).
Creano un oggetto Categorie / Prodotti e chiamano updateCategoria() o updateProdotto().
Anche qui, se tutto fila liscio, rispondono con {"success":"..."}, altrimenti con un errore.
VisualizzaCategorieServlet / VisualizzaProdottiServlet

Sul doGet interrogano il rispettivo CRUD (getAllCategorie() / getAllProdotti()), serializzano la lista in JSON con Gson e la inviano al client.
In caso di errore, inviano un JSON con {"error": "..."} e HTTP 500.
VisualizzaCategoriaByIdServlet / VisualizzaProdottoByIdServlet

Gestiscono la rotta GET /...ByIdServlet?id=XYZ.
Leggono l’id, chiamano i rispettivi metodi getCategoriaById(id), getProdottoById(id), e serializzano il risultato in JSON.
Se non trovato, restituiscono un 404.
Nota: Ogni servlet gestisce anche doOptions, dove si settano gli header CORS.

### 3 Il Database MySQL
## 3.1 Struttura delle Tabelle
- ** categorie:

id (PRIMARY KEY, auto-increment)
nome_categoria (VARCHAR unico)
prodotti:

id
nome
descrizione
prezzo
categoria_id come foreign key che punta a categorie.id

La clausola ON DELETE SET NULL fa sì che se la categoria viene eliminata, i prodotti che la referenziano avranno categoria_id = NULL.

## 3.2 Script di Creazione
Nel file CratesCreationDB.java troviamo un main che si connette al MySQL su localhost:3307 senza password. 
Esegue:

CREATE DATABASE IF NOT EXISTS crates
USE crates
CREATE TABLE IF NOT EXISTS categorie (...)
CREATE TABLE IF NOT EXISTS prodotti (...)
In questo modo, se il database o le tabelle non esistono, vengono creati. Se già esistono, non succede nulla.

### 4. Le Classi CRUD e di Utility
## 4.1 CategorieCRUD e ProdottiCRUD
Le classi in com.crates.crud contengono metodi DAO (Data Access Object) che si occupano di interagire direttamente con il DB.

- ** CategorieCRUD:

- ** getAllCategorie(): esegue una query SELECT * FROM categorie e restituisce la lista.
- ** addCategoria(categoria): INSERT INTO categorie (nome_categoria) VALUES (?).
- ** updateCategoria(categoria): UPDATE categorie SET nome_categoria = ? WHERE id = ?.
- ** deleteCategoria(id): DELETE FROM categorie WHERE id = ?.
- ** getCategoriaById(id): SELECT * FROM categorie WHERE id = ?.

- 
- ** ProdottiCRUD:
- ** getAllProdotti(), addProdotto(), updateProdotto(), deleteProdotto(), getProdottoById().
Usa la stessa logica di PreparedStatement, query parametriche, e cicli su ResultSet.
- ** LoggerUtil.log(...) viene chiamato per registrare alcune operazioni (come la modifica o l’eliminazione di un prodotto) in un file di log.
  
## 4.2 CratesConnectionDB
Classe di utility che fornisce il metodo getConnection() per connettersi a MySQL (url, user e password). Carica anche il driver com.mysql.cj.jdbc.Driver.

## 4.3 LoggerUtil
Scrive su un file operazioni_crud.log nella cartella temporanea del sistema (System.getProperty("java.io.tmpdir")). Aggiunge un timestamp e il messaggio di log. Viene usato dal CRUD (per esempio, quando si elimina un prodotto) per tenere traccia delle operazioni che l’utente effettua.

### 5. Flusso di Funzionamento: Esempio Concreto
## 5.1 Login e Navigazione
L’utente apre la webapp su Angular (http://localhost:4200).
Si presenta la pagina di login (LoginPageComponent).
Inserendo email e password corretta, localStorage salva isLoggedIn=true.
Il router reindirizza a /home, protetto da AuthGuard.

## 5.2 Visualizza Prodotti
L’utente clicca su “Visualizza Catalogo”.
Angular carica VisualizzaCatalogoComponent. All’ngOnInit, viene chiamato prodottiService.getProdotti().
Questo fa un HTTP GET a <http://localhost:8080/Crates/VisualizzaProdottiServlet>, la servlet che chiama getAllProdotti().
Il backend restituisce un JSON con la lista di prodotti, che Angular mostra in una tabella con sorting e paginazione.

## 5.3 Aggiungi Prodotto
L’utente clicca su “Aggiungi Prodotto” nella sidebar.
Angular mostra AggiungiProdottoComponent, con un form reattivo.
Compila “Nome”, “Descrizione”, “Prezzo” e “CategoriaId”, preme “Aggiungi Prodotto”.
Viene chiamato this.prodottiService.aggiungiProdotto(prodotto), che effettua una POST su AggiungiProdottoServlet.
La servlet fa addProdotto(prodotto) nel DB e risponde con un JSON success.
Angular avvisa l’utente con un alert e, se tutto ok, reindirizza a “visualizza-catalogo”.

## 5.4 Modifica / Elimina
In “Visualizza Catalogo”, c’è un tasto “edit” su ogni riga. Cliccandolo, si apre un BottomSheetMenuComponent.
L’utente può scegliere “Modifica” (router naviga a /modifica-prodotto/:id) oppure “Elimina” (viene aperta la ConfirmDialogComponent).
Su “Modifica Prodotto”, Angular mostra un form con i dati recuperati dal backend (getProdottoById). L’utente cambia i campi e preme “Salva”, scatenando una POST alla servlet ModificaProdottoServlet, che fa l’update nel DB.
Su “Elimina”, dopo la conferma, la servlet EliminaProdottoServlet chiama deleteProdotto(id). Se tutto va bene, logga l’operazione (LoggerUtil) e restituisce success.

##5.5 Categorie
Stessa identica logica di “Prodotti”, ma con i file dedicati (servlet, componenti, e CategorieService) per gestire le categorie.

###Conclusioni
La webapp Crates è un esempio completo di applicazione fullstack:

Frontend in Angular 15+ con standalone components, design Material, form reattivi e routing protetto da un semplice guard.
Backend con Java Servlet, ognuna specializzata nel gestire uno specifico endpoint.
DB MySQL con tabelle correlate e script di creazione.
Utilizzo di CORS e JSON come formato di scambio dati.
Log automatico di operazioni CRUD su file.
Il risultato è un gestionale di magazzino completo: login admin, visualizzazione prodotti, categorie, possibilità di modificare, aggiungere ed eliminare, e persino una vista “guest” che limita l’utente alla sola consultazione.

