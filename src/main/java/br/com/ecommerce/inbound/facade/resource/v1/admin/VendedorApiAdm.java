package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.common.enums.SortByAllowedFields;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.validator.SortByFields;
import br.com.ecommerce.inbound.dto.PessoaEditarRequest;
import br.com.ecommerce.inbound.dto.PessoaRequest;
import br.com.ecommerce.inbound.dto.PessoaResponse;
import br.com.ecommerce.inbound.dto.PessoaSearchCriteria;
import br.com.ecommerce.outbound.dto.PessoaResultResponse;
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
import java.util.List;
import java.util.Set;

import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.INVALID_SORT_FIELD;
import static br.com.ecommerce.inbound.facade.resource.v1.admin.ClienteResourceAdm.SORT_BY;

@Validated
@RequestMapping("v1/admin/vendedores")
public interface VendedorApiAdm {

    @Operation(summary = "Criar vendedor no sistema de ecommerce",
            description = "Retorna vendedor criado", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos retornados com sucesso",
                    content = @Content( mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PessoaResponse.class))))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<PessoaResponse> criar(@Valid @RequestBody PessoaRequest requisicao);


    @Operation(summary = "Operação responsável por alterar as informações do vendedor.",
            description = "alterar as informações do vendedor.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Vendedor inválido.") })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{}")
    public ServiceResponse<Vendedor> alterar(@PathVariable("idVendedor") String idVendedor, @RequestBody PessoaEditarRequest vendedor);


    @Operation(summary = "Operação responsável por excluir o vendedor.",
            description = "excluir o vendedor.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Vendedor inválido.") })
    @DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idVendedor}")
    public ServiceResponse<Boolean> excluir(@PathVariable("idVendedor") String idVendedor);

    @Operation(summary = "Operação responsável por consultar vendedor pelo nome.",
            description = "excluir o vendedor pelo nome.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "200", description = "Vendedor não encontrado.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{nome}")
    ServiceResponse<PessoaResponse> consultar(@PathVariable("nome") String nome);


    @Operation(summary = "Operação responsável por listar vendedores.",
            description = "por listar vendedores.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inválido.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServicePageableResponse<List<PessoaResultResponse>> listar(
            @Validated PessoaSearchCriteria searchCriteria,
            @RequestParam(value = SORT_BY, required = false)
            @SortByFields(enumClass = SortByAllowedFields.class, message = INVALID_SORT_FIELD)
                    Set<String> sortBy);
}
