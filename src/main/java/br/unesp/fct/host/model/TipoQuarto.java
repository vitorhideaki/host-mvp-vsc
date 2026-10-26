package br.unesp.fct.host.model;

/**
 * Tipos de quarto disponíveis para locação.
 */
public enum TipoQuarto {
    INDIVIDUAL("Quarto Individual"),
    COMPARTILHADO("Quarto Compartilhado"),
    SUITE("Suíte");

    private final String descricao;

    TipoQuarto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
