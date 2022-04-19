package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, String> {

    @Query("Select c from CategoriaProduto c where c.descCategoriaProduto like %:desc%")
    Optional<CategoriaProduto> findByDescCategoriaProduto(String desc);
}
