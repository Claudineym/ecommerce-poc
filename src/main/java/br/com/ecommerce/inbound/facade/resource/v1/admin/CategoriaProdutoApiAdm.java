package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
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
@RequestMapping("v1/admin/categoria-produtos")
public interface CategoriaProdutoApiAdm {

    @Operation(summary = "Criar categoria produto no sistema de ecommerce",
            description = "Retorna categoria produto criado", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos retornados com sucesso",
                    content = @Content( mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = ProdutoResponse.class))))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ServiceResponse<CategoriaProdutoResponse> criar(@Valid @RequestBody CategoriaProdutoRequest requisicao);


    @Operation(summary = "Opera????o respons??vel por alterar as informa????es da categoria produto.",
            description = "alterar as informa????es do produto.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categoria de produto alterada com sucesso."),
            @ApiResponse(responseCode = "400", description = "categoria de produto inv??lida.") })
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idCategoriaProduto}")
    public ServiceResponse<CategoriaProduto> alterar(@PathVariable("idCategoriaProduto") String idCategoriaProduto,
                                                     @RequestBody CategoriaProdutoEditarRequest categoriaProduto);


    @Operation(summary = "Opera????o respons??vel por excluir a categoria produto.",
            description = "excluir o produto.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Produto inv??lido.") })
    @DeleteMapping( produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/{idCategoriaProduto}")
    public ServiceResponse<Boolean> excluir(@PathVariable("idCategoriaProduto") String idCategoriaProduto);

    @Operation(summary = "Opera????o respons??vel por consultar a categoria de produto pelo descri????o.",
            description = "consultar a categoria de produto pelo descri????o", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "200", description = "Categoria do Produto n??o encontrado.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{descCategoriaProduto}")
    ServiceResponse<CategoriaProdutoResponse> consultar(@PathVariable("descCategoriaProduto") String descCategoriaProduto);


    @Operation(summary = "Opera????o respons??vel por listar categorias de produtos.",
            description = "por listar categorias de produtos.", security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "400", description = "Inv??lido.") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServiceResponse<List<CategoriaProdutoResponse>> listar();
}
