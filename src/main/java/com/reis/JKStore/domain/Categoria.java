package com.reis.JKStore.domain;

public enum Categoria {

    ELETRONICOS("Eletrônicos"),
    ROUPAS("Roupas"),
    ALIMENTOS("Alimentos"),
    MOVEIS("Móveis"),
    LIVROS("Livros"),
    BRINQUEDOS("Brinquedos"),
    COSMETICOS("Cosméticos"),
    ESPORTES("Esportes"),
    INFORMATICA("Informática"),
    AUTOMOTIVO("Automotivo"),
    OUTROS("Outros");

    private final String label;

    Categoria(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
