package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByNome(String nomeCliente);
}
