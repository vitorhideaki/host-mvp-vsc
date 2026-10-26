package br.unesp.fct.host.service;

import br.unesp.fct.host.dto.CadastroImovelDTO;
import br.unesp.fct.host.dto.CadastroQuartoDTO;
import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.Quarto;
import java.util.List;

/**
 * Interface de contrato para os serviços de cadastro de imóveis, quartos e consulta ao catálogo público.
 */
public interface AnuncioService {

    /**
     * Cadastra um novo imóvel vinculado ao anunciante autenticado.
     *
     * @param emailAnunciante e-mail do usuário com perfil de anunciante
     * @param dto             dados do imóvel a ser cadastrado
     * @return entidade {@link Imovel} persistida
     */
    Imovel cadastrarImovel(String emailAnunciante, CadastroImovelDTO dto);

    /**
     * Cadastra um novo quarto vinculado a um imóvel pertencente ao anunciante.
     *
     * @param emailAnunciante e-mail do anunciante proprietário do imóvel
     * @param dto             dados do quarto a ser cadastrado
     * @return entidade {@link Quarto} persistida
     */
    Quarto cadastrarQuarto(String emailAnunciante, CadastroQuartoDTO dto);

    /**
     * Lista todos os imóveis cadastrados pelo anunciante informado.
     *
     * @param emailAnunciante e-mail do anunciante
     * @return lista de imóveis do anunciante
     */
    List<Imovel> listarImoveisDoAnunciante(String emailAnunciante);

    /**
     * Lista todos os quartos pertencentes aos imóveis do anunciante informado.
     *
     * @param emailAnunciante e-mail do anunciante
     * @return lista de quartos do anunciante
     */
    List<Quarto> listarQuartosDoAnunciante(String emailAnunciante);

    /**
     * Retorna a lista pública de todos os quartos com status DISPONIVEL para consulta sem cadastro.
     *
     * @return lista de quartos disponíveis no catálogo público
     */
    List<Quarto> listarQuartosDisponiveisCatalogo();
}
