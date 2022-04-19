package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.common.enums.SortByAllowedFields;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.validator.SortByFields;
import br.com.ecommerce.inbound.dto.*;
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
@RequestMapping("v1/admin/produtos")
public interface ProdutoApiAdm {

    @Operation(summary = "Criar produto no sistema de ecommerce",
            description = "Retorna produto criado", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos retornados com sucesso",
                    content = @Content( mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProdutoResponse.class))))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest requisicao);


    @Operation(summary = "Operação responsável por alterar as informações do produto.",
            description = "alterar as informações do produto.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "produto alterado com sucesso."),
            @ApiResponse(responseCode = "400", description = "produto inválido.") })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idProduto}")
    public ServiceResponse<Produto> alterar(@PathVariable("idProduto") String idProduto, @RequestBody ProdutoEditarRequest produto);


    @Operation(summary = "Operação responsável por excluir o produto.",
            description = "excluir o produto.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Produto inválido.") })
    @DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idProduto}")
    public ServiceResponse<Boolean> excluir(@PathVariable("idProduto") String idProduto);

    @Operation(summary = "Operação responsável por consultar produto pelo nome.",
            description = "excluir o produto pelo nome.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "200", description = "Produto não encontrado.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{nomeProduto}")
    ServiceResponse<ProdutoResponse> consultar(@PathVariable("nomeProduto") String nomeProduto);


    @Operation(summary = "Operação responsável por listar produtos.",
            description = "por listar produtos.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inválido.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServicePageableResponse<List<ProdutoResponse>> listar(
            @Validated SearchCriteria searchCriteria,
            @RequestParam(value = SORT_BY, required = false)
            @SortByFields(enumClass = SortByAllowedFields.class, message = INVALID_SORT_FIELD)
                    Set<String> sortBy);
}
