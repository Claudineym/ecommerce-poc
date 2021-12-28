package br.com.ecommerce.business.domain.service.helper;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Endereco;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.inbound.dto.EnderecoRequest;
import br.com.ecommerce.inbound.dto.EnderecoResponse;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import com.google.common.base.Preconditions;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Data
@Component
public class ClienteServiceHelper {

    private static final String SORT_ORDER_REGEX = "^([a-zA-Z0-9]*)(\\.(asc|desc))?";
    private static final int FIELD_NAME_GROUP_ORDER = 1;
    private static final int SORT_ORDER_DIRECTION_GROUP_ORDER = 3;
    private static final String NOME = "nome";
    private static final String DEFAULT_ORDER_COLUMN = "idCliente.asc";


    public Page<ClienteResultResponse> toClienteResponse(
            Page<Cliente> clientes) {
        List<ClienteResultResponse> response =
                clientes.stream().map(this::buildClienteResult).collect(Collectors.toList());
        return new PageImpl<>(response);
    }

    public ClienteResultResponse buildClienteResult(Cliente cliente){
        return ClienteResultResponse
                .builder()
                .id(cliente.getIdCliente().toString())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .celular(cliente.getCelular())
                .enderecos(toEnderecoResponse(cliente.getEnderecos()))
                .build();
    }

    public Specification<Cliente> getCriteria(
            ClienteSearchCriteria searchCriteria) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(buildPredicates(root, criteriaBuilder, searchCriteria));
    }

    public Predicate[] buildPredicates(
            Root<Cliente> root,
            CriteriaBuilder criteriaBuilder,
            ClienteSearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList<>();

        return predicates.toArray(new Predicate[0]);
    }

    public PageRequest buildPaging(ClienteSearchCriteria criteria, Set<String> sortBy) {
        return PageRequest.of(
                criteria.getOffset(),
                criteria.getLimit(),
                Sort.by(new ArrayList<>(getSortedBy(sortBy))));
    }

    public Set<Sort.Order> getSortedBy(Set<String> fields) {
        return Optional.ofNullable(fields).orElse(Collections.singleton(DEFAULT_ORDER_COLUMN)).stream()
                .distinct()
                .map(this::getSortedBy)
                .collect(toCollection(LinkedHashSet::new));
    }

    public Sort.Order getSortedBy(String field) {
        var pattern = Pattern.compile(SORT_ORDER_REGEX, Pattern.MULTILINE);
        var matcher = pattern.matcher(field);

        Preconditions.checkNotNull(field);
        Preconditions.checkArgument(matcher.matches());

        String fieldName = matcher.group(FIELD_NAME_GROUP_ORDER);
        String sortOrderDirection = matcher.group(SORT_ORDER_DIRECTION_GROUP_ORDER);
        return buildSortOrder(fieldName, sortOrderDirection);
    }

    public Sort.Order buildSortOrder(String property, String direction) {
        return new Sort.Order(
                Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC),
                property,
                Sort.NullHandling.NATIVE);
    }

    public ClienteResponse toClienteResponse(Cliente cliente, Set<EnderecoResponse> enderecos){
        return  ClienteResponse.builder()
                .id(cliente.getIdCliente().toString())
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
}
