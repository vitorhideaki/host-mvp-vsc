package br.unesp.fct.host.service.impl;

import br.unesp.fct.host.dto.CadastroImovelDTO;
import br.unesp.fct.host.dto.CadastroQuartoDTO;
import br.unesp.fct.host.exception.RegraNegocioException;
import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.model.StatusQuarto;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.repository.ImovelRepository;
import br.unesp.fct.host.repository.QuartoRepository;
import br.unesp.fct.host.service.AnuncioService;
import br.unesp.fct.host.service.UsuarioService;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação dos serviços de cadastro de imóveis, quartos e consulta ao catálogo público.
 */
@Service
public class AnuncioServiceImpl implements AnuncioService {

    private static final Logger LOGGER = Logger.getLogger(AnuncioServiceImpl.class.getName());

    private final ImovelRepository imovelRepository;
    private final QuartoRepository quartoRepository;
    private final UsuarioService usuarioService;

    public AnuncioServiceImpl(ImovelRepository imovelRepository,
                              QuartoRepository quartoRepository,
                              UsuarioService usuarioService) {
        this.imovelRepository = imovelRepository;
        this.quartoRepository = quartoRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public Imovel cadastrarImovel(String emailAnunciante, CadastroImovelDTO dto) {
        Usuario anunciante = validarPerfilAnunciante(emailAnunciante);

        Imovel imovel = new Imovel(
                dto.getTitulo().trim(),
                dto.getEndereco().trim(),
                dto.getBairro().trim(),
                dto.getCidade().trim(),
                dto.getCep() != null ? dto.getCep().trim() : null,
                dto.getDescricao() != null ? dto.getDescricao().trim() : null,
                anunciante
        );

        Imovel salvo = imovelRepository.save(imovel);
        LOGGER.info("Imóvel cadastrado com sucesso: " + salvo.getResumoIdentificacao());
        return salvo;
    }

    @Override
    @Transactional
    public Quarto cadastrarQuarto(String emailAnunciante, CadastroQuartoDTO dto) {
        Usuario anunciante = validarPerfilAnunciante(emailAnunciante);

        Imovel imovel = imovelRepository.findByIdAndAnunciante(dto.getImovelId(), anunciante)
                .orElseThrow(() -> new RegraNegocioException(
                        "Imóvel não encontrado ou não pertence ao anunciante autenticado."));

        Quarto quarto = new Quarto(
                dto.getIdentificacao().trim(),
                dto.getTipo(),
                dto.getCapacidade(),
                dto.getComodidades().trim(),
                dto.getValorMensal(),
                dto.getRegras() != null ? dto.getRegras().trim() : null,
                imovel
        );

        Quarto salvo = quartoRepository.save(quarto);
        LOGGER.info("Quarto cadastrado com sucesso: " + salvo.getResumoIdentificacao());
        return salvo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Imovel> listarImoveisDoAnunciante(String emailAnunciante) {
        Usuario anunciante = usuarioService.buscarPorEmail(emailAnunciante);
        return imovelRepository.findByAnuncianteOrderByDataCriacaoDesc(anunciante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quarto> listarQuartosDoAnunciante(String emailAnunciante) {
        Usuario anunciante = usuarioService.buscarPorEmail(emailAnunciante);
        return quartoRepository.findByAnunciante(anunciante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quarto> listarQuartosDisponiveisCatalogo() {
        return quartoRepository.findByStatusComImovel(StatusQuarto.DISPONIVEL);
    }

    private Usuario validarPerfilAnunciante(String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (!usuario.isAnunciante()) {
            LOGGER.warning("Tentativa de cadastro de anúncio por usuário sem perfil de anunciante: " + email);
            throw new RegraNegocioException(
                    "Apenas usuários com perfil de Anunciante podem cadastrar imóveis e quartos.");
        }
        return usuario;
    }
}
