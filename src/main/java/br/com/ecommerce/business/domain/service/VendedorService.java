package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.business.domain.repository.PagingAndSortingVendedorRepository;
import br.com.ecommerce.business.domain.repository.VendedorRepository;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.inbound.dto.PessoaSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VendedorService {

    private final PagingAndSortingVendedorRepository pagingAndSortingRepository;
    private final ClienteServiceHelper helper;
    private final VendedorRepository repository;

    public Vendedor criar(Vendedor vendedor) {
       return repository.save(vendedor);
    }

    public Page<Vendedor> listar(Specification<Vendedor> criteria, PessoaSearchCriteria searchCriteria, Set<String> sortBy) {
        var paging = helper.buildPaging(searchCriteria, sortBy == null ? Set.of("id") : sortBy);
        Page<Vendedor> pages = pagingAndSortingRepository.findAll(criteria, paging);
        return pages;
    }


    public Vendedor alterar(Vendedor vendedor) {
        return repository.save(vendedor);
    }


    public void excluir(Vendedor vendedor) {
        repository.delete(vendedor);
    }
}
