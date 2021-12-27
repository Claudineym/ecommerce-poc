package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteEditarRequest;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClienteService {

    ServiceResponse<ClienteResponse> criar(ClienteRequest requisicao);

    ServicePageableResponse<List<ClienteResultResponse>> listar(
            ClienteSearchCriteria searchCriteria, Set<String> sortBy);

    ServiceResponse<ClienteResponse> consultar(String nomeCliente);

    ServiceResponse<Cliente> alterar(String idCliente, ClienteEditarRequest clienteEditarRequest);

    ServiceResponse<Boolean> excluir(String idCliente);
}
