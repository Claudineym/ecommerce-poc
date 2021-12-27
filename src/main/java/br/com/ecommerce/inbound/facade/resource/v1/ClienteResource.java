package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.business.domain.service.ClienteServiceImpl;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@Data
@RestController
public class ClienteResource implements ClienteApi {

    private final ClienteServiceImpl clienteService;

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
