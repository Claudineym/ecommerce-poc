package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingClienteRepository  extends CrudRepository<Cliente, String>,
        PagingAndSortingRepository<Cliente, String>,
        JpaSpecificationExecutor<Cliente> {
}
