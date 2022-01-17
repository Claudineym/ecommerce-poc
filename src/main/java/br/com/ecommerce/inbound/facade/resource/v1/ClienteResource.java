package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.inbound.dto.EnderecoResponse;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@Data
@RestController
public class ClienteResource implements ClienteApi {

    private final ClienteService service;
    private final ClienteRepository repository;
    private final ClienteServiceHelper helper;

    @Override
    public ServiceResponse<ClienteResponse> consultar(String nomeCliente) {
        log.debug("Consultar cliente: {}", nomeCliente);
        final ServiceResponse<ClienteResponse> serviceResponse = new ServiceResponse<>();

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
    public ServicePageableResponse<List<ClienteResultResponse>> listar(ClienteSearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<ClienteResultResponse>> response =
                new ServicePageableResponse<>();

        Specification<Cliente> criteria = helper.getCriteria(searchCriteria);

        Page<Cliente> pages = service.listar(criteria, searchCriteria, sortBy);
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

}
