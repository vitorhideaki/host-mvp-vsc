package br.unesp.fct.host.model;

/**
 * Perfis de usuário suportados pela plataforma.
 */
public enum PerfilUsuario {
    USUARIO_CADASTRADO("Usuário Cadastrado"),
    ANUNCIANTE("Anunciante"),
    INQUILINO("Inquilino"),
    ADMINISTRADOR("Administrador");

    private final String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
