package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.PagingAndSortingClienteRepository;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
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

    public Page<Cliente> listar(Specification<Cliente> criteria, ClienteSearchCriteria searchCriteria, Set<String> sortBy) {
        var paging = helper.buildPaging(searchCriteria, sortBy);
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
