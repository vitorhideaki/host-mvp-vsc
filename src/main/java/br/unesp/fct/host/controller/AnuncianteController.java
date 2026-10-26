package br.unesp.fct.host.controller;

import br.unesp.fct.host.dto.CadastroImovelDTO;
import br.unesp.fct.host.dto.CadastroQuartoDTO;
import br.unesp.fct.host.exception.RegraNegocioException;
import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.model.TipoQuarto;
import br.unesp.fct.host.service.AnuncioService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC para gestão de imóveis e quartos por usuários com perfil de Anunciante.
 */
@Controller
@RequestMapping("/anunciante")
public class AnuncianteController {

    private final AnuncioService anuncioService;

    public AnuncianteController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    @GetMapping("/painel")
    public String exibirPainelAnunciante(Principal principal, Model model) {
        String email = principal.getName();
        List<Imovel> imoveis = anuncioService.listarImoveisDoAnunciante(email);
        List<Quarto> quartos = anuncioService.listarQuartosDoAnunciante(email);

        model.addAttribute("imoveis", imoveis);
        model.addAttribute("quartos", quartos);
        return "anunciante/painel";
    }

    @GetMapping("/imoveis/novo")
    public String exibirFormularioNovoImovel(Model model) {
        model.addAttribute("imovelDTO", new CadastroImovelDTO());
        return "anunciante/form-imovel";
    }

    @PostMapping("/imoveis/novo")
    public String salvarNovoImovel(@Valid @ModelAttribute("imovelDTO") CadastroImovelDTO dto,
                                   BindingResult bindingResult,
                                   Principal principal,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "anunciante/form-imovel";
        }

        try {
            Imovel imovel = anuncioService.cadastrarImovel(principal.getName(), dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Imóvel '" + imovel.getTitulo() + "' cadastrado com sucesso! Agora você pode adicionar quartos a ele.");
            return "redirect:/anunciante/painel";
        } catch (RegraNegocioException ex) {
            model.addAttribute("mensagemErro", ex.getMessage());
            return "anunciante/form-imovel";
        }
    }

    @GetMapping("/quartos/novo")
    public String exibirFormularioNovoQuarto(@RequestParam(name = "imovelId", required = false) Long imovelId,
                                             Principal principal,
                                             Model model,
                                             RedirectAttributes redirectAttributes) {
        List<Imovel> imoveis = anuncioService.listarImoveisDoAnunciante(principal.getName());
        if (imoveis.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro",
                    "Cadastre pelo menos um imóvel antes de cadastrar um quarto.");
            return "redirect:/anunciante/imoveis/novo";
        }

        CadastroQuartoDTO quartoDTO = new CadastroQuartoDTO();
        if (imovelId != null) {
            quartoDTO.setImovelId(imovelId);
        } else {
            quartoDTO.setImovelId(imoveis.get(0).getId());
        }

        model.addAttribute("quartoDTO", quartoDTO);
        model.addAttribute("imoveis", imoveis);
        model.addAttribute("tiposQuarto", TipoQuarto.values());
        return "anunciante/form-quarto";
    }

    @PostMapping("/quartos/novo")
    public String salvarNovoQuarto(@Valid @ModelAttribute("quartoDTO") CadastroQuartoDTO dto,
                                   BindingResult bindingResult,
                                   Principal principal,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("imoveis", anuncioService.listarImoveisDoAnunciante(principal.getName()));
            model.addAttribute("tiposQuarto", TipoQuarto.values());
            return "anunciante/form-quarto";
        }

        try {
            Quarto quarto = anuncioService.cadastrarQuarto(principal.getName(), dto);
            redirectAttributes.addFlashAttribute("mensagemSucesso",
                    "Quarto '" + quarto.getIdentificacao() + "' cadastrado com sucesso e publicado no catálogo!");
            return "redirect:/anunciante/painel";
        } catch (RegraNegocioException ex) {
            model.addAttribute("mensagemErro", ex.getMessage());
            model.addAttribute("imoveis", anuncioService.listarImoveisDoAnunciante(principal.getName()));
            model.addAttribute("tiposQuarto", TipoQuarto.values());
            return "anunciante/form-quarto";
        }
    }
}
