package br.unesp.fct.host.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um imóvel cadastrado por um anunciante.
 */
@Entity
@Table(name = "imoveis")
public class Imovel extends EntidadeBase {

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 250)
    private String endereco;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(length = 15)
    private String cep;

    @Column(length = 500)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anunciante_id", nullable = false)
    private Usuario anunciante;

    @OneToMany(mappedBy = "imovel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> quartos = new ArrayList<>();

    public Imovel() {
    }

    public Imovel(String titulo, String endereco, String bairro, String cidade, String cep, String descricao, Usuario anunciante) {
        this.titulo = titulo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.descricao = descricao;
        this.anunciante = anunciante;
    }

    /**
     * Retorna a localização aproximada (bairro e cidade) para exibição pública
     * no catálogo sem expor o endereço exato do imóvel.
     *
     * @return string no formato "Bairro - Cidade"
     */
    public String getLocalizacaoPublica() {
        return String.format("%s - %s", bairro, cidade);
    }

    @Override
    public String getResumoIdentificacao() {
        return String.format("%s (%s - %s)", titulo, bairro, cidade);
    }

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

    public Usuario getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Usuario anunciante) {
        this.anunciante = anunciante;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }
}
