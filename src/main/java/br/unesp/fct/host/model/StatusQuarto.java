package br.unesp.fct.host.model;

/**
 * Status de disponibilidade de um quarto.
 */
public enum StatusQuarto {
    DISPONIVEL("Disponível"),
    RESERVADO("Reservado"),
    OCUPADO("Ocupado"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    StatusQuarto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
