package br.unesp.fct.host.service.impl;

import br.unesp.fct.host.model.StatusUsuario;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.repository.UsuarioRepository;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço de carregamento de credenciais para autenticação no Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class.getName());

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    LOGGER.warning("Falha de autenticação - e-mail não encontrado: " + email);
                    return new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
                });

        boolean contaAtiva = usuario.getStatus() == StatusUsuario.ATIVO;
        String role = "ROLE_" + usuario.getPerfil().name();

        return new User(
                usuario.getEmail(),
                usuario.getSenhaHash(),
                contaAtiva,
                true,
                true,
                contaAtiva,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
