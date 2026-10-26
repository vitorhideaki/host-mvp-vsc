package br.unesp.fct.host.service.impl;

import br.unesp.fct.host.dto.CadastroUsuarioDTO;
import br.unesp.fct.host.dto.HabilitarAnuncianteDTO;
import br.unesp.fct.host.exception.RegraNegocioException;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.repository.UsuarioRepository;
import br.unesp.fct.host.service.UsuarioService;
import java.util.logging.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação das regras de negócio de contas e perfis de usuários.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioServiceImpl.class.getName());

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario cadastrarUsuario(CadastroUsuarioDTO dto) {
        String emailNormalizado = dto.getEmail().trim().toLowerCase();
        if (usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            LOGGER.warning("Tentativa de cadastro com e-mail já existente: " + emailNormalizado);
            throw new RegraNegocioException("Já existe uma conta cadastrada com este endereço de e-mail.");
        }

        String senhaHash = passwordEncoder.encode(dto.getSenha());
        Usuario novoUsuario = new Usuario(dto.getNome().trim(), emailNormalizado, senhaHash);
        Usuario salvo = usuarioRepository.save(novoUsuario);
        LOGGER.info("Novo usuário cadastrado com sucesso: " + salvo.getResumoIdentificacao());
        return salvo;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o e-mail informado."));
    }

    @Override
    @Transactional
    public Usuario habilitarComoAnunciante(String email, HabilitarAnuncianteDTO dto) {
        Usuario usuario = buscarPorEmail(email);
        usuario.habilitarComoAnunciante(
                dto.getCpfCnpj().trim(),
                dto.getTelefone().trim(),
                dto.getComprovanteResidencia().trim(),
                dto.getChavePix().trim()
        );
        Usuario atualizado = usuarioRepository.save(usuario);
        LOGGER.info("Usuário habilitado como Anunciante: " + atualizado.getResumoIdentificacao());
        return atualizado;
    }
}
