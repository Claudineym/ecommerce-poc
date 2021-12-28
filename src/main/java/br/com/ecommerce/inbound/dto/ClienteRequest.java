package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Sexo;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Builder
@Data
public class ClienteRequest {
    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
    private Set<EnderecoRequest> enderecos;
}
