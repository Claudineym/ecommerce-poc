package br.com.ecommerce.business.domain.service.helper;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.inbound.dto.CategoriaProdutoResponse;
import br.com.ecommerce.inbound.dto.ProdutoResponse;
import br.com.ecommerce.inbound.dto.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaProdutoServiceHelper {

    public Specification<CategoriaProduto> getCriteria(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(buildPredicates(root, criteriaBuilder, searchCriteria));
    }

    public Predicate[] buildPredicates(
            Root<CategoriaProduto> root,
            CriteriaBuilder criteriaBuilder,
            SearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList<>();

        return predicates.toArray(new Predicate[0]);
    }

    public CategoriaProdutoResponse toClienteResponse(CategoriaProduto categoriaProduto){
       return CategoriaProdutoResponse.builder()
                .idCategoriaProduto(categoriaProduto.getIdCategoriaProduto())
                .descCategoriaProduto(categoriaProduto.getDescCategoriaProduto())
                .build();
    }

    public List<CategoriaProdutoResponse> toCategoriaProdutoResponse(
            List<CategoriaProduto> produtos) {
        List<CategoriaProdutoResponse> response =
                produtos.stream().map(this::buildResult).collect(Collectors.toList());
        return response;
    }

    public CategoriaProdutoResponse buildResult(CategoriaProduto categoriaProduto){
        return CategoriaProdutoResponse.builder()
                .idCategoriaProduto(categoriaProduto.getIdCategoriaProduto())
                .descCategoriaProduto(categoriaProduto.getDescCategoriaProduto())
                .build();
    }

    public List<CategoriaProdutoResponse> toCategoriaProdutoResponses(List<CategoriaProduto> categorias){
        List<CategoriaProdutoResponse> lista = new ArrayList<>();
        for(CategoriaProduto categoriaProduto : categorias){
            lista.add(buildResult(categoriaProduto));
        }
        return lista;
    }
}