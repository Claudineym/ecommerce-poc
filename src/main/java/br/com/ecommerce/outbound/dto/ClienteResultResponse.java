package br.com.ecommerce.outbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(defaultValue = "clienteResultResponse")
public class ClienteResultResponse {
    private String id;
    private String nome;
    private String celular;
    private String email;
}
