package br.com.ecommerce.inbound.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClienteResponse {
    private String nome;
    private String celular;
    private String email;
}
