package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingVendedorRepository extends CrudRepository<Vendedor, String>,
        PagingAndSortingRepository<Vendedor, String>,
        JpaSpecificationExecutor<Vendedor> {
}
