package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.PessoaResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Data
@RestController
public class ClienteResourceAdm implements ClienteApiAdm {

    public static final String SORT_BY = "sort_by";
    public static final String INVALID_SORT_FIELD = "Campo para ordenação inválido";

    private final ClienteRepository repository;
    private final ClienteService clienteService;
    private final ClienteServiceHelper helper;

    @Override
    public ServiceResponse<PessoaResponse> criar(PessoaRequest requisicao) {
        log.debug("Criar cliente: {}", requisicao);
        var cliente = requisicao.toCliente(helper);

        var clienteRetorno = clienteService.criar(cliente);

        Set<EnderecoResponse> enderecosResp = helper.toEnderecoResponse(clienteRetorno.getEnderecos());

        return ServiceResponse.<PessoaResponse>builder()
                .result(helper.toClienteResponse(clienteRetorno, enderecosResp)
                ).mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }

    @Override
    public ServiceResponse<Cliente> alterar(String idCliente, PessoaEditarRequest clienteEditarRequest) {
        log.debug("Alterar cliente: {}", idCliente);
        final ServiceResponse<Cliente> serviceResponse = new ServiceResponse<>();
        Optional<Cliente> clienteOptional = repository.findById(idCliente);

        if(!clienteOptional.isPresent()){
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        Cliente cliente = clienteService.alterar(clienteEditarRequest.updateCliente(clienteOptional.get()));

        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(cliente);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> excluir(String idCliente) {
        log.debug("Remoção do cliente: {}", idCliente);
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        Optional<Cliente> clienteOptional = repository.findById(idCliente);
        if(!clienteOptional.isPresent()){

            serviceResponse.setResult(Boolean.FALSE);
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);

            return serviceResponse;
        }

        clienteService.excluir(clienteOptional.get());
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(Boolean.TRUE);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;

    }

    @Override
    public ServiceResponse<PessoaResponse> consultar(String nomeCliente) {
        log.debug("Consultar cliente: {}", nomeCliente);
        final ServiceResponse<PessoaResponse> serviceResponse = new ServiceResponse<>();

        Optional<Cliente> clienteOpt = repository.findByNome(nomeCliente);

        if(clienteOpt.isPresent()){
            Cliente cliente = clienteOpt.get();

            Set<EnderecoResponse> enderecos = helper.toEnderecoResponse(cliente.getEnderecos());

            serviceResponse.setResult(helper.toClienteResponse(cliente, enderecos));
            serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
            serviceResponse.setStatus(HttpStatus.OK);
            log.debug("Resultado da consulta de clientes: {}", serviceResponse.getResult());
            return serviceResponse;
        }

        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nomeCliente));

        return serviceResponse;
    }

    @Override
    public ServicePageableResponse<List<PessoaResultResponse>> listar(PessoaSearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<PessoaResultResponse>> response =
                new ServicePageableResponse<>();

        Specification<Cliente> criteria = helper.getCriteria(searchCriteria);

        Page<Cliente> pages = clienteService.listar(criteria, searchCriteria, sortBy);
        response.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());

        var pageMetadata =
                new PageMetadata(
                        pages.getTotalElements(),
                        (long) searchCriteria.getOffset(),
                        (long) searchCriteria.getLimit());

        Page<PessoaResultResponse> docs = helper.toClienteResponse(pages);

        response.setMetadata(pageMetadata);
        response.setResult(docs.getContent());

        return response;
    }
}
