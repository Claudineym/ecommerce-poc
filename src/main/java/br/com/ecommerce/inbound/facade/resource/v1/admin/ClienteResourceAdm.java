package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.service.ClienteServiceImpl;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteEditarRequest;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@Data
@RestController
public class ClienteResourceAdm implements ClienteApiAdm{

    public static final String SORT_BY = "sort_by";
    public static final String INVALID_SORT_FIELD = "Campo para ordenação inválido";

    private final ClienteServiceImpl clienteService;

    @Override
    public ServiceResponse<ClienteResponse> criar(ClienteRequest requisicao) {
        log.debug("Criar cliente: {}", requisicao);
        return clienteService.criar(requisicao);
    }

    @Override
    public ServiceResponse<Cliente> alterar(String idCliente, ClienteEditarRequest cliente) {
        log.debug("Alterar cliente: {}", idCliente);
        return clienteService.alterar(idCliente, cliente);
    }

    @Override
    public ServiceResponse<Boolean> excluir(String idCliente) {
        log.debug("Remoção do cliente: {}", idCliente);
        return clienteService.excluir(idCliente);
    }

    @Override
    public ServiceResponse<ClienteResponse> consultar(String nomeCliente) {
        log.debug("Consultar cliente: {}", nomeCliente);
        return clienteService.consultar(nomeCliente);
    }

    @Override
    public ServicePageableResponse<List<ClienteResultResponse>> listar(ClienteSearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<ClienteResultResponse>> response =
                clienteService.listar(searchCriteria, sortBy);
        return response;
    }
}
