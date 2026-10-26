package br.unesp.fct.host.repository;

import br.unesp.fct.host.model.Quarto;
import br.unesp.fct.host.model.StatusQuarto;
import br.unesp.fct.host.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA para operações de persistência da entidade {@link Quarto}.
 */
@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {

    @Query("SELECT q FROM Quarto q JOIN FETCH q.imovel i WHERE q.status = :status ORDER BY q.dataCriacao DESC")
    List<Quarto> findByStatusComImovel(@Param("status") StatusQuarto status);

    @Query("SELECT q FROM Quarto q JOIN FETCH q.imovel i WHERE i.anunciante = :anunciante ORDER BY q.dataCriacao DESC")
    List<Quarto> findByAnunciante(@Param("anunciante") Usuario anunciante);
}
