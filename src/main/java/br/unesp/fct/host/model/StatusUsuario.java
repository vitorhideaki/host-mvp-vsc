package br.unesp.fct.host.model;

/**
 * Status da conta de um usuário no sistema.
 */
public enum StatusUsuario {
    ATIVO("Ativo"),
    SUSPENSO("Suspenso"),
    INATIVO("Inativo");

    private final String descricao;

    StatusUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
