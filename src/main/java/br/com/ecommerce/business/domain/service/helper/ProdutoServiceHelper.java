package br.com.ecommerce.business.domain.service.helper;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.inbound.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProdutoServiceHelper {

    public Specification<Produto> getCriteria(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(buildPredicates(root, criteriaBuilder, searchCriteria));
    }

    public Predicate[] buildPredicates(
            Root<Produto> root,
            CriteriaBuilder criteriaBuilder,
            SearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList<>();

        return predicates.toArray(new Predicate[0]);
    }

    public ProdutoResponse toClienteResponse(Produto produto){
        return  ProdutoResponse.builder()
                .idProduto(produto.getIdProduto())
                .nome(produto.getNome())
                .descProduto(produto.getDescProduto())
                .tags(produto.getTags())
                .build();
    }


    public Page<ProdutoResponse> toProdutoResponse(
            Page<Produto> produtos) {
        List<ProdutoResponse> response =
                produtos.stream().map(this::buildResult).collect(Collectors.toList());
        return new PageImpl<>(response);
    }

    public ProdutoResponse buildResult(Produto produto){
        return ProdutoResponse.builder()
                .idProduto(produto.getIdProduto())
                .nome(produto.getNome())
                .descProduto(produto.getDescProduto())
                .tags(produto.getTags())
                .build();
    }
}