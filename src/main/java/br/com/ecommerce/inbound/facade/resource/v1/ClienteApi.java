package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.common.enums.SortByAllowedFields;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.validator.SortByFields;
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

import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.INVALID_SORT_FIELD;
import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.SORT_BY;

@Validated
@RequestMapping("v1/clientes")
public interface ClienteApi {

    @Operation(summary = "Operação responsável por consultar cliente pelo nome.",
            description = "excluir o cliente pelo nome.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "200", description = "Cliente não encontrado.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{nomeCliente}")
    ServiceResponse<ClienteResponse> consultar(@PathVariable("nomeCliente") String nomeCliente);


    @Operation(summary = "Operação responsável por listar clientes.",
            description = "por listar clientes.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inválido.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServicePageableResponse<List<ClienteResultResponse>> listar(
            @Validated ClienteSearchCriteria searchCriteria,
            @RequestParam(value = SORT_BY, required = false)
            @SortByFields(enumClass = SortByAllowedFields.class, message = INVALID_SORT_FIELD)
                    Set<String> sortBy);
}
