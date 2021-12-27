package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Validated
@RequestMapping("v1/clientes")
public interface ClienteApi {

    @Operation(summary = "Operação responsável por consultar cliente pelo nome.",
            description = "excluir o cliente pelo nome.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Cliente inválido.") })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{nomeCliente}")
    ServiceResponse<ClienteResponse> consultar(@PathVariable("nomeCliente") String nomeCliente);


    @Operation(summary = "Operação responsável por listar clientes.",
            description = "por listar clientes.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inválido.") })
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ServicePageableResponse<List<ClienteResultResponse>> listar(
            ClienteSearchCriteria searchCriteria, Set<String> sortBy);
}
