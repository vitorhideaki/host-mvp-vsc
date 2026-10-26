package br.unesp.fct.host.controller;

import br.unesp.fct.host.dto.CadastroUsuarioDTO;
import br.unesp.fct.host.dto.HabilitarAnuncianteDTO;
import br.unesp.fct.host.exception.RegraNegocioException;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC responsável pelo cadastro, login e gestão de perfil de usuários.
 */
@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String exibirFormularioLogin() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("cadastroDTO", new CadastroUsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@Valid @ModelAttribute("cadastroDTO") CadastroUsuarioDTO dto,
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cadastro";
        }

        try {
            usuarioService.cadastrarUsuario(dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Cadastro realizado com sucesso! Faça login com seu e-mail e senha.");
            return "redirect:/login";
        } catch (RegraNegocioException ex) {
            model.addAttribute("mensagemErro", ex.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/perfil")
    public String exibirPerfil(Principal principal, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
        HabilitarAnuncianteDTO dto = new HabilitarAnuncianteDTO();
        dto.setCpfCnpj(usuario.getCpfCnpj());
        dto.setTelefone(usuario.getTelefone());
        dto.setComprovanteResidencia(usuario.getComprovanteResidencia());
        dto.setChavePix(usuario.getChavePix());

        model.addAttribute("usuario", usuario);
        model.addAttribute("anuncianteDTO", dto);
        return "perfil";
    }

    @PostMapping("/perfil/tornar-anunciante")
    public String habilitarComoAnunciante(@Valid @ModelAttribute("anuncianteDTO") HabilitarAnuncianteDTO dto,
                                          BindingResult bindingResult,
                                          Principal principal,
                                          HttpServletRequest request,
                                          Model model,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Usuario usuario = usuarioService.buscarPorEmail(principal.getName());
            model.addAttribute("usuario", usuario);
            return "perfil";
        }

        Usuario atualizado = usuarioService.habilitarComoAnunciante(principal.getName(), dto);

        // Atualiza a sessão do Spring Security com a nova permissão ROLE_ANUNCIANTE imediatamente
        Authentication authAtual = SecurityContextHolder.getContext().getAuthentication();
        if (authAtual != null) {
            String novaRole = "ROLE_" + atualizado.getPerfil().name();
            Authentication novaAuth = new UsernamePasswordAuthenticationToken(
                    authAtual.getPrincipal(),
                    authAtual.getCredentials(),
                    List.of(new SimpleGrantedAuthority(novaRole))
            );
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(novaAuth);
            SecurityContextHolder.setContext(context);
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        }

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Perfil de Anunciante habilitado com sucesso! Agora você já pode cadastrar imóveis e quartos.");
        return "redirect:/anunciante/painel";
    }

    @GetMapping("/acesso-negado")
    public String exibirAcessoNegado() {
        return "acesso-negado";
    }
}
