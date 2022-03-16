package br.com.ecommerce.inbound.dto;

import lombok.*;

import java.util.Set;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendedorResponse {
    private String id;
    private String nome;
    private String celular;
    private String email;
    private Set<EnderecoResponse> enderecos;
}
