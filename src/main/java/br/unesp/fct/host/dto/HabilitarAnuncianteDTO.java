package br.unesp.fct.host.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para habilitação de um usuário cadastrado como anunciante.
 */
public class HabilitarAnuncianteDTO {

    @NotBlank(message = "Campo CPF/CNPJ vazio. Por favor, preencha antes de salvar.")
    @Size(min = 11, max = 25, message = "Informe um CPF ou CNPJ válido.")
    private String cpfCnpj;

    @NotBlank(message = "Campo telefone de contato vazio. Por favor, preencha antes de salvar.")
    @Size(max = 30, message = "O telefone deve ter no máximo 30 caracteres.")
    private String telefone;

    @NotBlank(message = "Campo comprovante de residência vazio. Por favor, preencha antes de salvar.")
    @Size(max = 200, message = "A identificação do comprovante deve ter no máximo 200 caracteres.")
    private String comprovanteResidencia;

    @NotBlank(message = "Campo chave PIX vazio. Por favor, preencha antes de salvar.")
    @Size(max = 120, message = "A chave PIX deve ter no máximo 120 caracteres.")
    private String chavePix;

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getComprovanteResidencia() {
        return comprovanteResidencia;
    }

    public void setComprovanteResidencia(String comprovanteResidencia) {
        this.comprovanteResidencia = comprovanteResidencia;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }
}
