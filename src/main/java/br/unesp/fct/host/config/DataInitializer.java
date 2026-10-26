package br.unesp.fct.host.config;

import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.model.TipoQuarto;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.repository.ImovelRepository;
import br.unesp.fct.host.repository.QuartoRepository;
import br.unesp.fct.host.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.util.logging.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Carga inicial de dados para demonstração imediata do MVP (Catálogo Público, Usuários e Imóveis).
 */
@Configuration
public class DataInitializer {

    private static final Logger LOGGER = Logger.getLogger(DataInitializer.class.getName());

    @Bean
    public CommandLineRunner carregarDadosIniciais(UsuarioRepository usuarioRepository,
                                                   ImovelRepository imovelRepository,
                                                   QuartoRepository quartoRepository,
                                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() > 0) {
                return;
            }

            // 1. Usuário Anunciante de exemplo
            Usuario anunciante = new Usuario(
                    "Carlos Eduardo Silva",
                    "anunciante@host.com",
                    passwordEncoder.encode("Senha@123456")
            );
            anunciante.habilitarComoAnunciante(
                    "123.456.789-00",
                    "(18) 99700-1122",
                    "Conta de Energia CPFL #98412",
                    "anunciante@host.com"
            );
            usuarioRepository.save(anunciante);

            // 2. Usuário Comum (Estudante) de exemplo
            Usuario estudante = new Usuario(
                    "Mariana Souza",
                    "estudante@host.com",
                    passwordEncoder.encode("Senha@123456")
            );
            usuarioRepository.save(estudante);

            // 3. Imóvel 1 próximo à FCT UNESP
            Imovel imovel1 = new Imovel(
                    "República Universitária Sol Nascente",
                    "Rua Roberto Simonsen, 450",
                    "Jardim das Rosas",
                    "Presidente Prudente - SP",
                    "19060-080",
                    "Imóvel amplo a 5 minutos a pé da UNESP FCT, ambiente tranquilo para estudos.",
                    anunciante
            );
            imovelRepository.save(imovel1);

            Quarto quarto1 = new Quarto(
                    "Quarto Individual 01 - Mobiliado",
                    TipoQuarto.INDIVIDUAL,
                    1,
                    "Cama box, escrivaninha, guarda-roupa, Wi-Fi 500Mb, ventilador de teto",
                    new BigDecimal("650.00"),
                    "Proibido fumar no interior. Silêncio após as 22h.",
                    imovel1
            );
            quartoRepository.save(quarto1);

            Quarto quarto2 = new Quarto(
                    "Quarto Compartilhado 02 (2 Vagas)",
                    TipoQuarto.COMPARTILHADO,
                    2,
                    "Duas camas de solteiro, duas bancadas de estudo, ar-condicionado, Wi-Fi",
                    new BigDecimal("480.00"),
                    "Divisão de limpeza semanal das áreas comuns.",
                    imovel1
            );
            quartoRepository.save(quarto2);

            // 4. Imóvel 2
            Imovel imovel2 = new Imovel(
                    "Casa Compartilhada Vila Universitária",
                    "Rua Padre João Goetz, 820",
                    "Vila Euclides",
                    "Presidente Prudente - SP",
                    "19061-460",
                    "Casa térrea com cozinha completa, lavanderia e garagem coberta.",
                    anunciante
            );
            imovelRepository.save(imovel2);

            Quarto quarto3 = new Quarto(
                    "Suíte Master Individual",
                    TipoQuarto.SUITE,
                    1,
                    "Banheiro privativo, ar-condicionado, cama de casal, armário embutido, internet fibra",
                    new BigDecimal("890.00"),
                    "Contas de água e internet inclusas no valor.",
                    imovel2
            );
            quartoRepository.save(quarto3);

            LOGGER.info("Dados iniciais de demonstração do MVP carregados com sucesso.");
        };
    }
}
