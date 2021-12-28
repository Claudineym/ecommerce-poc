package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Sexo;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class ClienteEditarRequest {
    private String nome;
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
    private Set<EnderecoRequest> enderecos;
}
