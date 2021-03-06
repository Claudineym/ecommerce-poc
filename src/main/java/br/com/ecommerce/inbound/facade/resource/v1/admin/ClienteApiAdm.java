package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.common.enums.SortByAllowedFields;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.validator.SortByFields;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.INVALID_SORT_FIELD;
import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.SORT_BY;

import java.util.List;
import java.util.Set;

@Validated
@RequestMapping("v1/admin/clientes")
public interface ClienteApiAdm {

    @Operation(summary = "Criar cliente no sistema de ecommerce",
            description = "Retorna cliente criado", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos retornados com sucesso",
                    content = @Content( mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ClienteResponse.class))))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<ClienteResponse> criar(@Valid @RequestBody ClienteRequest requisicao);


    @Operation(summary = "Opera????o respons??vel por alterar as informa????es do cliente.",
            description = "alterar as informa????es do cliente.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Cliente inv??lido.") })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idCliente}")
    public ServiceResponse<Cliente> alterar(@PathVariable("idCliente") String idCliente, @RequestBody ClienteEditarRequest cliente);


    @Operation(summary = "Opera????o respons??vel por excluir o cliente.",
            description = "excluir o cliente.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Cliente inv??lido.") })
    @DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idCliente}")
    public ServiceResponse<Boolean> excluir(@PathVariable("idCliente") String idCliente);

    @Operation(summary = "Opera????o respons??vel por consultar cliente pelo nome.",
            description = "excluir o cliente pelo nome.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "200", description = "Cliente n??o encontrado.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{nomeCliente}")
    ServiceResponse<ClienteResponse> consultar(@PathVariable("nomeCliente") String nomeCliente);


    @Operation(summary = "Opera????o respons??vel por listar clientes.",
            description = "por listar clientes.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inv??lido.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServicePageableResponse<List<ClienteResultResponse>> listar(
            @Validated SearchCriteria searchCriteria,
            @RequestParam(value = SORT_BY, required = false)
            @SortByFields(enumClass = SortByAllowedFields.class, message = INVALID_SORT_FIELD)
                    Set<String> sortBy);

    @Operation(summary = "Transformar o cliente em Vendedor no sistema de ecommerce",
            description = "Retorna vendedor", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedores retornados com sucesso",
                    content = @Content( mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ClienteResponse.class))))
    })
    @PutMapping(path = "/{idCliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<VendedorResponse> transformarEmVendedor(@PathVariable("idCliente") String idCliente);
}
