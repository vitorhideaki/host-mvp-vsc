package br.unesp.fct.host.dto;

import br.unesp.fct.host.model.TipoQuarto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO para cadastro de quartos em imóveis de anunciantes.
 */
public class CadastroQuartoDTO {

    @NotNull(message = "Selecione o imóvel ao qual o quarto pertence.")
    private Long imovelId;

    @NotBlank(message = "Campo identificação do quarto vazio. Por favor, preencha antes de salvar.")
    @Size(max = 120, message = "A identificação deve ter no máximo 120 caracteres.")
    private String identificacao;

    @NotNull(message = "Selecione o tipo do quarto.")
    private TipoQuarto tipo = TipoQuarto.INDIVIDUAL;

    @NotNull(message = "Informe a capacidade do quarto.")
    @Min(value = 1, message = "A capacidade mínima do quarto é de 1 pessoa.")
    private Integer capacidade = 1;

    @NotBlank(message = "Campo comodidades vazio. Por favor, preencha antes de salvar.")
    @Size(max = 300, message = "As comodidades devem ter no máximo 300 caracteres.")
    private String comodidades;

    @NotNull(message = "Informe o valor mensal do aluguel.")
    @DecimalMin(value = "0.01", message = "O valor do aluguel deve ser maior que zero.")
    private BigDecimal valorMensal;

    @Size(max = 400, message = "As regras devem ter no máximo 400 caracteres.")
    private String regras;

    public Long getImovelId() {
        return imovelId;
    }

    public void setImovelId(Long imovelId) {
        this.imovelId = imovelId;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getComodidades() {
        return comodidades;
    }

    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(BigDecimal valorMensal) {
        this.valorMensal = valorMensal;
    }

    public String getRegras() {
        return regras;
    }

    public void setRegras(String regras) {
        this.regras = regras;
    }
}
