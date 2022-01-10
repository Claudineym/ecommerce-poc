package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
@RestController
public class ClienteResourceAdm implements ClienteApiAdm {

    public static final String SORT_BY = "sort_by";
    public static final String INVALID_SORT_FIELD = "Campo para ordenação inválido";

    private final ClienteService clienteService;
    private final ClienteServiceHelper helper;

    @Override
    public ServiceResponse<ClienteResponse> criar(ClienteRequest requisicao) {
        log.debug("Criar cliente: {}", requisicao);

        var cliente = requisicao.toCliente();

        var clienteRetorno = clienteService.criar(cliente);

        Set<EnderecoResponse> enderecosResp = helper.toEnderecoResponse(clienteRetorno.getEnderecos());

        return ServiceResponse.<ClienteResponse>builder()
                .result(helper.toClienteResponse(clienteRetorno, enderecosResp)
                ).mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
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
