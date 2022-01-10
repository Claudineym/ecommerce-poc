package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Sexo;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Builder
public class ClienteRequest {

    private final ClienteServiceHelper helper;

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
    private Set<EnderecoRequest> enderecos;

    public Cliente toCliente(){
        Cliente cliente =  Cliente
                            .builder()
                            .idCliente(UUID.randomUUID().toString())
                            .nome(this.nome)
                            .email(this.email)
                            .celular(this.celular)
                            .dtNascimento(this.dtNascimento)
                            .build();

        cliente.setEnderecos(helper.toEndereco(this.enderecos));

        return cliente;
    }
}