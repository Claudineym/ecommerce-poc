package br.com.ecommerce.outbound.dto;

import br.com.ecommerce.inbound.dto.EnderecoResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(defaultValue = "pessoaResultResponse")
public class PessoaResultResponse {
    private String id;
    private String nome;
    private String celular;
    private String email;
    private Set<EnderecoResponse> enderecos;
}
