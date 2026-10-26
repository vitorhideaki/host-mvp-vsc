package br.unesp.fct.host.repository;

import br.unesp.fct.host.model.Imovel;
import br.unesp.fct.host.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para operações de persistência da entidade {@link Imovel}.
 */
@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    List<Imovel> findByAnuncianteOrderByDataCriacaoDesc(Usuario anunciante);

    Optional<Imovel> findByIdAndAnunciante(Long id, Usuario anunciante);
}
