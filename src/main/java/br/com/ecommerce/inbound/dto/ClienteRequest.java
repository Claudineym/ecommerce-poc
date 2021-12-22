package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Sexo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClienteRequest {
    @NotNull @NotEmpty
    private String nome;
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
}
