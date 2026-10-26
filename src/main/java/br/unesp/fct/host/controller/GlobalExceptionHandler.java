package br.unesp.fct.host.controller;

import br.unesp.fct.host.exception.RegraNegocioException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Tratamento centralizado de exceções da aplicação web.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(RegraNegocioException.class)
    public String tratarRegraNegocio(RegraNegocioException ex, Model model) {
        LOGGER.log(Level.WARNING, "Violação de regra de negócio: {0}", ex.getMessage());
        model.addAttribute("tituloErro", "Operação não permitida");
        model.addAttribute("mensagemErro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(Exception.class)
    public String tratarErroGeral(Exception ex, Model model) {
        LOGGER.log(Level.SEVERE, "Erro inesperado no processamento da requisição", ex);
        model.addAttribute("tituloErro", "Erro no processamento");
        model.addAttribute("mensagemErro",
                "Ocorreu um erro inesperado ao processar sua solicitação. Verifique os dados informados e tente novamente.");
        return "erro";
    }
}
