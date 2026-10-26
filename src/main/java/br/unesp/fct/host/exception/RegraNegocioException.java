package br.unesp.fct.host.exception;

/**
 * Exceção lançada quando uma regra de negócio do domínio é violada.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
