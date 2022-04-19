package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.repository.PagingAndSortingProdutoRepository;
import br.com.ecommerce.business.domain.repository.ProdutoRepository;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.inbound.dto.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final PagingAndSortingProdutoRepository pagingAndSortingProdutoRepository;
    private final UtilServiceHelper helper;
    private final ProdutoRepository repository;

    public Produto criar(Produto produto) {
       return repository.save(produto);
    }

    public Page<Produto> listar(Specification<Produto> criteria, SearchCriteria searchCriteria, Set<String> sortBy) {
        var paging = helper.buildPaging(searchCriteria, sortBy == null ? Set.of("id") : sortBy);
        Page<Produto> pages = pagingAndSortingProdutoRepository.findAll(criteria, paging);
        return pages;
    }


    public Produto alterar(Produto produto) {
        return repository.save(produto);
    }


    public void excluir(Produto produto) {
        repository.delete(produto);
    }

}
