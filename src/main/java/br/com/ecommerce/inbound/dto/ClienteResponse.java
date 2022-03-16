package br.com.ecommerce.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private String id;
    private String nome;
    private String celular;
    private String email;
    private Set<EnderecoResponse> enderecos;
}
