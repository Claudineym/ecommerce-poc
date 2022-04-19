package br.com.ecommerce.business.domain.repository;

import br.com.ecommerce.business.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PagingAndSortingProdutoRepository extends CrudRepository<Produto, String>,
        PagingAndSortingRepository<Produto, String>,
        JpaSpecificationExecutor<Produto> {
}
