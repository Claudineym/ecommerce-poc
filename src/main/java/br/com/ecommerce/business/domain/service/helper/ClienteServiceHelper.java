package br.com.ecommerce.business.domain.service.helper;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Endereco;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
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
public class ClienteServiceHelper {

    public Page<ClienteResultResponse> toClienteResponse(
            Page<Cliente> clientes) {
        List<ClienteResultResponse> response =
                clientes.stream().map(this::buildClienteResult).collect(Collectors.toList());
        return new PageImpl<>(response);
    }

    public ClienteResultResponse buildClienteResult(Cliente cliente){
        return ClienteResultResponse
                .builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .celular(cliente.getCelular())
                .enderecos(toEnderecoResponse(cliente.getEnderecos()))
                .build();
    }

    public Specification<Cliente> getCriteria(SearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(buildPredicates(root, criteriaBuilder, searchCriteria));
    }

    public Predicate[] buildPredicates(
            Root<Cliente> root,
            CriteriaBuilder criteriaBuilder,
            SearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList<>();

        return predicates.toArray(new Predicate[0]);
    }

    public ClienteResponse toClienteResponse(Cliente cliente, Set<EnderecoResponse> enderecos){
        return  ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .celular(cliente.getCelular())
                .email(cliente.getEmail())
                .enderecos(enderecos).build();
    }

    public Set<EnderecoResponse> toEnderecoResponse(Set<Endereco> enderecos){
        return enderecos
                .stream().map(e->{
                    return EnderecoResponse
                            .builder()
                            .cep(e.getCep())
                            .endereco(e.getEndereco())
                            .complemento(e.getComplemento())
                            .numero(e.getNumero())
                            .bairro(e.getBairro())
                            .cidade(e.getCidade())
                            .estado(e.getEstado())
                            .build();
                }).collect(Collectors.toSet());
    }

    public Set<Endereco> toEndereco(Set<EnderecoRequest> enderecos){
        return  enderecos
                .stream()
                .map(e->{
                    return Endereco
                            .builder()
                            .idEstado(UUID.randomUUID().toString())
                            .cep(e.getCep())
                            .bairro(e.getBairro())
                            .cidade(e.getCidade())
                            .complemento(e.getComplemento())
                            .endereco(e.getEndereco())
                            .estado(e.getEstado())
                            .numero(e.getNumero())
                            .endereco(e.getEndereco())
                            .build();
                }).collect(Collectors.toSet());
    }

    public VendedorResponse toVendedorResponse(Vendedor vendedor){
        return  VendedorResponse.builder()
                .id(vendedor.getId())
                .nome(vendedor.getNome())
                .celular(vendedor.getCelular())
                .email(vendedor.getEmail()).build();
    }

}
