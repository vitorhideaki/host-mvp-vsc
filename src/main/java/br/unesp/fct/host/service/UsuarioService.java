package br.unesp.fct.host.service;

import br.unesp.fct.host.dto.CadastroUsuarioDTO;
import br.unesp.fct.host.dto.HabilitarAnuncianteDTO;
import br.unesp.fct.host.model.Usuario;

/**
 * Interface de contrato para os serviços relacionados a contas e perfis de usuários.
 */
public interface UsuarioService {

    /**
     * Realiza o cadastro de um novo usuário na plataforma com senha criptografada.
     *
     * @param dto dados informados no formulário de cadastro
     * @return entidade {@link Usuario} persistida
     */
    Usuario cadastrarUsuario(CadastroUsuarioDTO dto);

    /**
     * Busca um usuário cadastrado pelo seu e-mail.
     *
     * @param email e-mail do usuário
     * @return entidade {@link Usuario} encontrada
     */
    Usuario buscarPorEmail(String email);

    /**
     * Habilita o perfil de anunciante para um usuário já cadastrado, salvando seus dados adicionais.
     *
     * @param email e-mail do usuário autenticado
     * @param dto   dados adicionais exigidos para anunciante (CPF/CNPJ, telefone, comprovante e PIX)
     * @return entidade {@link Usuario} atualizada com perfil ANUNCIANTE
     */
    Usuario habilitarComoAnunciante(String email, HabilitarAnuncianteDTO dto);
}
