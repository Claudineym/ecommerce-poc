package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.PagingAndSortingClienteRepository;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.ClienteEditarRequest;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{

    private final PagingAndSortingClienteRepository pagingAndSortingClienteRepository;
    private final ClienteServiceHelper helper;
    private final ClienteRepository repository;

    @Override
    public ServiceResponse<ClienteResponse> criar(ClienteRequest requisicao) {

        Cliente  cliente = repository.save(Cliente
                .builder()
                .idCliente(UUID.randomUUID().toString())
                .nome(requisicao.getNome())
                .email(requisicao.getEmail())
                .celular(requisicao.getCelular())
                .dtNascimento(requisicao.getDtNascimento())
                .build());

        return ServiceResponse.<ClienteResponse>builder()
                .result(ClienteResponse.builder()
                        .nome(cliente.getNome())
                        .celular(cliente.getCelular())
                        .email(cliente.getEmail())
                        .build()
                ).mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }

    @Override
    public ServicePageableResponse<List<ClienteResultResponse>> listar(ClienteSearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<ClienteResultResponse>> response =
                new ServicePageableResponse<>();

        Specification<Cliente> criteria = helper.getCriteria(searchCriteria);

        var paging = helper.buildPaging(searchCriteria, sortBy);

        Page<Cliente> pages = pagingAndSortingClienteRepository.findAll(criteria, paging);
        response.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());

        var pageMetadata =
                new PageMetadata(
                        pages.getTotalElements(),
                        (long) searchCriteria.getOffset(),
                        (long) searchCriteria.getLimit());

        Page<ClienteResultResponse> docs = helper.toClienteResponse(pages);

        response.setMetadata(pageMetadata);
        response.setResult(docs.getContent());
        return response;
    }

    @Override
    public ServiceResponse<ClienteResponse> consultar(String nomeCliente) {
        final ServiceResponse<ClienteResponse> serviceResponse = new ServiceResponse<>();

        Optional<Cliente> clienteOpt = repository.findByNome(nomeCliente);

        if(clienteOpt.isPresent()){
            Cliente cliente = clienteOpt.get();
            serviceResponse.setResult( ClienteResponse.builder()
                    .id(cliente.getIdCliente().toString())
                    .nome(cliente.getNome())
                    .celular(cliente.getCelular())
                    .email(cliente.getEmail()).build());
            serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
            serviceResponse.setStatus(HttpStatus.OK);
            log.debug("Resultado da consulta de clientes: {}", serviceResponse.getResult());
            return serviceResponse;
        }

        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
        serviceResponse.setStatus(HttpStatus.NOT_FOUND);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Cliente> alterar(String idCliente, ClienteEditarRequest clienteEditarRequest) {
        final ServiceResponse<Cliente> serviceResponse = new ServiceResponse<>();
        Optional<Cliente> clienteOptional = repository.findById(idCliente);
        if(!clienteOptional.isPresent()){
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);

            return serviceResponse;
        }

        Cliente clienteDB = clienteOptional.get();
        Cliente clienteUpdated = new Cliente();

        clienteUpdated.setIdCliente(clienteDB.getIdCliente());

        if(Objects.nonNull(clienteEditarRequest.getCelular()) ){
            if (!clienteEditarRequest.getCelular().equals(clienteDB.getCelular())) {
                clienteUpdated.setCelular(clienteEditarRequest.getCelular());
            }
        }
        if(Objects.nonNull(clienteEditarRequest.getSexo())){
            if(!clienteEditarRequest.getSexo().equals(clienteDB.getSexo())){
                clienteUpdated.setSexo(clienteEditarRequest.getSexo());
            }
        }
        if(Objects.nonNull(clienteEditarRequest.getEmail())){
            if(!clienteEditarRequest.getEmail().equals(clienteDB.getEmail())){
                clienteUpdated.setEmail(clienteEditarRequest.getEmail());
            }
        }
        if(Objects.nonNull(clienteEditarRequest.getDtNascimento())){
            if(!clienteEditarRequest.getDtNascimento().equals(clienteDB.getDtNascimento())){
                clienteUpdated.setDtNascimento(clienteEditarRequest.getDtNascimento());
            }
        }
        if(Objects.nonNull(clienteEditarRequest.getNome())){
            if(!clienteEditarRequest.getNome().equals(clienteDB.getNome())){
                clienteUpdated.setNome(clienteEditarRequest.getNome());
            }
        }

        Cliente cliente = repository.save(clienteUpdated);
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(cliente);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> excluir(String idCliente) {
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        Optional<Cliente> clienteOptional = repository.findById(idCliente);
        if(!clienteOptional.isPresent()){

            serviceResponse.setResult(Boolean.FALSE);
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);

            return serviceResponse;
        }

        repository.delete(clienteOptional.get());
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(Boolean.TRUE);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }
}
