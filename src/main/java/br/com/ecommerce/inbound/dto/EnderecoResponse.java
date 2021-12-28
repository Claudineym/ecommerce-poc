package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Estado;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EnderecoResponse {
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private Estado estado;
}
