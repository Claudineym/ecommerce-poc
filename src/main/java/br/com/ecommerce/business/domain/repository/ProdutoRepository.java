package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    @Query("Select c from Produto c where c.nome like %:nome%")
    Optional<Produto> findByNome(String nome);
}
