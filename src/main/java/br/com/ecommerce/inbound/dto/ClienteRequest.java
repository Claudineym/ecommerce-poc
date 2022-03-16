package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Sexo;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.business.domain.service.helper.VendedorServiceHelper;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
public class ClienteRequest {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
    private Set<EnderecoRequest> enderecos;

    public Cliente toCliente(ClienteServiceHelper helper){
        return new Cliente(this.nome, this.sexo, this.dtNascimento, this.celular,
                this.email, helper.toEndereco(this.enderecos));
    }
}