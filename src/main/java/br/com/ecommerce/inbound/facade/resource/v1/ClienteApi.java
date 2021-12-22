package br.com.ecommerce.inbound.facade.resource.v1;

import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("v1/clientes")
public interface ClienteApi {

    @Operation(
            summary =
                    "Criar cliente no sistema de ecommerce",
            description = "Retorna cliente criado",
            tags = {"ecommerce"},
            security = {@SecurityRequirement(name = "Bearer")})
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Documentos retornados com sucesso",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array =
                                    @ArraySchema(
                                            schema = @Schema(implementation = ClienteResponse.class))))
            })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServiceResponse<ClienteResponse> criar(
            @Validated @RequestBody ClienteRequest requisicao);
}
