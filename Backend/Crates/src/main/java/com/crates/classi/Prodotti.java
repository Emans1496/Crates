package com.crates.classi;

public class Prodotti {
    private int id;
    private String nome;
    private String descrizione;
    private double prezzo;
    private int categoriaId;

    // Costruttore di default
    public Prodotti() {}

    // Costruttore con parametri
    public Prodotti(int id, String nome, String descrizione, double prezzo, int categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoriaId = categoriaId;
    }

    // Getter e Setter vari
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    // toString() per debug/log
    @Override
    public String toString() {
        return "Prodotti{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", prezzo=" + prezzo +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
