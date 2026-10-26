package br.unesp.fct.host.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um usuário cadastrado na plataforma.
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends EntidadeBase {

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PerfilUsuario perfil = PerfilUsuario.USUARIO_CADASTRADO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusUsuario status = StatusUsuario.ATIVO;

    // Dados adicionais para habilitacao como anunciante
    @Column(name = "cpf_cnpj", length = 25)
    private String cpfCnpj;

    @Column(length = 30)
    private String telefone;

    @Column(name = "comprovante_residencia", length = 200)
    private String comprovanteResidencia;

    @Column(name = "chave_pix", length = 120)
    private String chavePix;

    @OneToMany(mappedBy = "anunciante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imovel> imoveis = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = PerfilUsuario.USUARIO_CADASTRADO;
        this.status = StatusUsuario.ATIVO;
    }

    /**
     * Habilita o usuário como anunciante preenchendo os dados adicionais obrigatórios.
     *
     * @param cpfCnpj               CPF ou CNPJ do anunciante
     * @param telefone              Telefone principal de contato
     * @param comprovanteResidencia Identificação/descrição do comprovante de residência
     * @param chavePix              Chave PIX para recebimento
     */
    public void habilitarComoAnunciante(String cpfCnpj, String telefone, String comprovanteResidencia, String chavePix) {
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.comprovanteResidencia = comprovanteResidencia;
        this.chavePix = chavePix;
        this.perfil = PerfilUsuario.ANUNCIANTE;
    }

    public boolean isAnunciante() {
        return this.perfil == PerfilUsuario.ANUNCIANTE || this.perfil == PerfilUsuario.ADMINISTRADOR;
    }

    @Override
    public String getResumoIdentificacao() {
        return String.format("%s (%s) - Perfil: %s", nome, email, perfil.getDescricao());
    }

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

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

    public StatusUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusUsuario status) {
        this.status = status;
    }

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

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
}
