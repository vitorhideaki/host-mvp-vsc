package br.unesp.fct.host.controller;

import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.service.AnuncioService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador MVC responsável pelo Catálogo Público de quartos disponíveis.
 */
@Controller
public class CatalogoController {

    private final AnuncioService anuncioService;

    public CatalogoController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }

    /**
     * Exibe o catálogo público de quartos disponíveis sem exigir cadastro ou autenticação.
     */
    @GetMapping({"/", "/catalogo"})
    public String exibirCatalogoPublico(Model model) {
        List<Quarto> quartosDisponiveis = anuncioService.listarQuartosDisponiveisCatalogo();
        model.addAttribute("quartos", quartosDisponiveis);
        return "catalogo";
    }
}
