package br.unesp.fct.host.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;

/**
 * Entidade que representa um quarto em um imóvel compartilhado.
 * Inclui controle de versão otimista (@Version).
 */
@Entity
@Table(name = "quartos")
public class Quarto extends EntidadeBase {

    @Column(nullable = false, length = 120)
    private String identificacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoQuarto tipo = TipoQuarto.INDIVIDUAL;

    @Column(nullable = false)
    private Integer capacidade = 1;

    @Column(nullable = false, length = 300)
    private String comodidades;

    @Column(name = "valor_mensal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMensal;

    @Column(length = 400)
    private String regras;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusQuarto status = StatusQuarto.DISPONIVEL;

    @Version
    private Long versao;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "imovel_id", nullable = false)
    private Imovel imovel;

    public Quarto() {
    }

    public Quarto(String identificacao, TipoQuarto tipo, Integer capacidade, String comodidades,
                  BigDecimal valorMensal, String regras, Imovel imovel) {
        this.identificacao = identificacao;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.comodidades = comodidades;
        this.valorMensal = valorMensal;
        this.regras = regras;
        this.status = StatusQuarto.DISPONIVEL;
        this.imovel = imovel;
    }

    @Override
    public String getResumoIdentificacao() {
        return String.format("%s - %s (R$ %s)", identificacao, tipo.getDescricao(), valorMensal);
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

    public StatusQuarto getStatus() {
        return status;
    }

    public void setStatus(StatusQuarto status) {
        this.status = status;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}
