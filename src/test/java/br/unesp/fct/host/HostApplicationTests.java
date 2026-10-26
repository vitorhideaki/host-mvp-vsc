package br.unesp.fct.host;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.unesp.fct.host.dto.CadastroImovelDTO;
import br.unesp.fct.host.dto.CadastroQuartoDTO;
import br.unesp.fct.host.dto.CadastroUsuarioDTO;
import br.unesp.fct.host.dto.HabilitarAnuncianteDTO;
import br.unesp.fct.host.exception.RegraNegocioException;
import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.PerfilUsuario;
import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.model.StatusQuarto;
import br.unesp.fct.host.model.TipoQuarto;
import br.unesp.fct.host.model.Usuario;
import br.unesp.fct.host.service.AnuncioService;
import br.unesp.fct.host.service.UsuarioService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testes unitários e de integração do MVP.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HostApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnuncioService anuncioService;

    @Test
    @DisplayName("Teste unitário dos modelos de domínio e polimorfismo (Usuario, Imovel, Quarto)")
    void deveTestarModelosDominioEPolimorfismo() {
        Usuario usuario = new Usuario("Erina Sakakida", "erina@unesp.br", "hash123");
        assertFalse(usuario.isAnunciante());
        assertEquals(PerfilUsuario.USUARIO_CADASTRADO, usuario.getPerfil());

        usuario.habilitarComoAnunciante("111.222.333-44", "(18) 99999-1111", "CPFL #123", "erina@unesp.br");
        assertTrue(usuario.isAnunciante());
        assertEquals(PerfilUsuario.ANUNCIANTE, usuario.getPerfil());

        Imovel imovel = new Imovel(
                "República FCT",
                "Rua Roberto Simonsen, 305",
                "Centro Educacional",
                "Presidente Prudente - SP",
                "19060-900",
                "Próximo à UNESP",
                usuario
        );
        assertEquals("Centro Educacional - Presidente Prudente - SP", imovel.getLocalizacaoPublica());

        Quarto quarto = new Quarto(
                "Quarto 01",
                TipoQuarto.INDIVIDUAL,
                1,
                "Wi-Fi e escrivaninha",
                new BigDecimal("600.00"),
                "Silêncio após 22h",
                imovel
        );
        assertEquals(StatusQuarto.DISPONIVEL, quarto.getStatus());
        assertTrue(quarto.getResumoIdentificacao().contains("Quarto 01"));
    }

    @Test
    @DisplayName("Visitante não autenticado deve consultar catálogo público de quartos disponíveis")
    void devePermitirConsultaPublicaDoCatalogoSemCadastro() throws Exception {
        mockMvc.perform(get("/catalogo"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalogo"));

        List<Quarto> disponiveis = anuncioService.listarQuartosDisponiveisCatalogo();
        assertFalse(disponiveis.isEmpty(), "O catálogo público deve retornar quartos disponíveis");
    }

    @Test
    @DisplayName("Deve cadastrar novo usuário e habilitar perfil de anunciante")
    void deveCadastrarUsuarioEHabilitarPerfilAnunciante() {
        CadastroUsuarioDTO dto = new CadastroUsuarioDTO();
        dto.setNome("Vitor Tsunoda");
        dto.setEmail("vitor@unesp.br");
        dto.setSenha("SenhaSegura123");

        Usuario salvo = usuarioService.cadastrarUsuario(dto);
        assertNotNull(salvo.getId());
        assertEquals(PerfilUsuario.USUARIO_CADASTRADO, salvo.getPerfil());

        HabilitarAnuncianteDTO anuncianteDTO = new HabilitarAnuncianteDTO();
        anuncianteDTO.setCpfCnpj("999.888.777-66");
        anuncianteDTO.setTelefone("(18) 98888-7777");
        anuncianteDTO.setComprovanteResidencia("Conta Sabesp #4567");
        anuncianteDTO.setChavePix("vitor@unesp.br");

        Usuario atualizado = usuarioService.habilitarComoAnunciante("vitor@unesp.br", anuncianteDTO);
        assertEquals(PerfilUsuario.ANUNCIANTE, atualizado.getPerfil());
    }

    @Test
    @DisplayName("Usuário com perfil de Anunciante deve cadastrar imóvel e quarto")
    void devePermitirAnuncianteCadastrarImovelEQuarto() {
        CadastroImovelDTO imovelDTO = new CadastroImovelDTO();
        imovelDTO.setTitulo("Residencial Universitário Prudente");
        imovelDTO.setEndereco("Rua José Bongiovani, 1200");
        imovelDTO.setBairro("Cidade Universitária");
        imovelDTO.setCidade("Presidente Prudente - SP");
        imovelDTO.setCep("19050-680");
        imovelDTO.setDescricao("Casa ampla com quintal");

        Imovel imovel = anuncioService.cadastrarImovel("anunciante@host.com", imovelDTO);
        assertNotNull(imovel.getId());

        CadastroQuartoDTO quartoDTO = new CadastroQuartoDTO();
        quartoDTO.setImovelId(imovel.getId());
        quartoDTO.setIdentificacao("Quarto Compartilhado 03");
        quartoDTO.setTipo(TipoQuarto.COMPARTILHADO);
        quartoDTO.setCapacidade(2);
        quartoDTO.setComodidades("Beliche, armário, internet fibra");
        quartoDTO.setValorMensal(new BigDecimal("520.00"));
        quartoDTO.setRegras("Não fumar");

        Quarto quarto = anuncioService.cadastrarQuarto("anunciante@host.com", quartoDTO);
        assertNotNull(quarto.getId());
        assertEquals(StatusQuarto.DISPONIVEL, quarto.getStatus());
    }

    @Test
    @DisplayName("Usuário comum sem perfil de Anunciante não pode cadastrar imóvel")
    void deveImpedirUsuarioComumDeCadastrarImovel() {
        CadastroImovelDTO imovelDTO = new CadastroImovelDTO();
        imovelDTO.setTitulo("Imóvel Não Autorizado");
        imovelDTO.setEndereco("Rua Teste, 10");
        imovelDTO.setBairro("Centro");
        imovelDTO.setCidade("Presidente Prudente - SP");

        assertThrows(RegraNegocioException.class, () ->
                anuncioService.cadastrarImovel("estudante@host.com", imovelDTO)
        );
    }

    @Test
    @WithMockUser(username = "anunciante@host.com", roles = {"ANUNCIANTE"})
    @DisplayName("Painel do anunciante deve abrir para usuário com ROLE_ANUNCIANTE")
    void deveAcessarPainelAnuncianteQuandoAutorizado() throws Exception {
        mockMvc.perform(get("/anunciante/painel"))
                .andExpect(status().isOk())
                .andExpect(view().name("anunciante/painel"));

        mockMvc.perform(post("/anunciante/imoveis/novo")
                        .with(csrf())
                        .param("titulo", "Moradia Teste MVC")
                        .param("endereco", "Av. Brasil, 500")
                        .param("bairro", "Vila Nova")
                        .param("cidade", "Presidente Prudente - SP"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/anunciante/painel"));
    }
}
