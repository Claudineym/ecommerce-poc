package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    @Query("Select c from Cliente c where c.nome like %:nomeCliente%")
    Optional<Cliente> findByNome(String nomeCliente);
}
