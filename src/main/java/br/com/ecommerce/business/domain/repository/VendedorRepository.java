package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, String> {

    @Query("Select c from Vendedor c where c.nome like %:nome%")
    Optional<Vendedor> findByNome(String nome);
}
