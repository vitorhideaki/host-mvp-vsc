package br.unesp.fct.host.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para o formulário de cadastro de novos usuários.
 */
public class CadastroUsuarioDTO {

    @NotBlank(message = "Campo nome vazio. Por favor, preencha antes de salvar.")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    private String nome;

    @NotBlank(message = "Campo e-mail vazio. Por favor, preencha antes de salvar.")
    @Email(message = "Informe um endereço de e-mail válido.")
    private String email;

    @NotBlank(message = "Campo senha vazio. Por favor, preencha antes de salvar.")
    @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres.")
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
