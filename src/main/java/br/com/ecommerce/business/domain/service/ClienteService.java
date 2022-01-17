package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.PagingAndSortingClienteRepository;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.inbound.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final PagingAndSortingClienteRepository pagingAndSortingClienteRepository;
    private final ClienteServiceHelper helper;
    private final ClienteRepository repository;

    public Cliente criar(Cliente cliente) {
       return repository.save(cliente);
    }

    public Page<Cliente> listar(Specification<Cliente> criteria, PessoaSearchCriteria searchCriteria, Set<String> sortBy) {
        var paging = helper.buildPaging(searchCriteria, sortBy == null ? Set.of("id") : sortBy);
        Page<Cliente> pages = pagingAndSortingClienteRepository.findAll(criteria, paging);
        return pages;
    }


    public Cliente alterar(Cliente cliente) {
        return repository.save(cliente);
    }


    public void excluir(Cliente cliente) {
        repository.delete(cliente);
    }
}
