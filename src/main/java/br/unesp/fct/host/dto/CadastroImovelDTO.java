package br.unesp.fct.host.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para cadastro de imóveis por anunciantes.
 */
public class CadastroImovelDTO {

    @NotBlank(message = "Campo título do imóvel vazio. Por favor, preencha antes de salvar.")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
    private String titulo;

    @NotBlank(message = "Campo endereço vazio. Por favor, preencha antes de salvar.")
    @Size(max = 250, message = "O endereço deve ter no máximo 250 caracteres.")
    private String endereco;

    @NotBlank(message = "Campo bairro vazio. Por favor, preencha antes de salvar.")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres.")
    private String bairro;

    @NotBlank(message = "Campo cidade vazio. Por favor, preencha antes de salvar.")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    private String cidade;

    @Size(max = 15, message = "O CEP deve ter no máximo 15 caracteres.")
    private String cep;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String descricao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
