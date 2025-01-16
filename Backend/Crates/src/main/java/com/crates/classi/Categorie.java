package com.crates.classi;

public class Categorie {
    private int id;
    private String nomeCategoria;

    // Costruttore di default
    public Categorie() {}

    // Costruttore con parametri
    public Categorie(int id, String nomeCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
    }

    // Getter e Setter per ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter e Setter per nomeCategoria
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    // Metodo toString() per debug/log
    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                '}';
    }
}
