package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Sexo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class ClienteEditarRequest {

    @JsonProperty private String nome;
    @JsonProperty private Sexo sexo;
    @JsonProperty private Date dtNascimento;
    @JsonProperty private String celular;
    @JsonProperty private String email;
    @JsonProperty private Set<EnderecoRequest> enderecos;

    public Cliente updateCliente(Cliente cliente) {
        if(Objects.nonNull(this.celular) ){
            if (!this.celular.equals(cliente.getCelular())) {
                cliente.setCelular(this.celular);
            }
        }
        if(Objects.nonNull(this.sexo)){
            if(!this.sexo.equals(cliente.getSexo())){
                cliente.setSexo(this.sexo);
            }
        }
        if(Objects.nonNull(this.email)){
            if(!this.email.equals(cliente.getEmail())){
                cliente.setEmail(this.email);
            }
        }
        if(Objects.nonNull(this.dtNascimento)){
            if(!this.dtNascimento.equals(cliente.getDtNascimento())){
                cliente.setDtNascimento(this.dtNascimento);
            }
        }
        if(Objects.nonNull(this.nome)){
            if(!this.nome.equals(cliente.getNome())){
                cliente.setNome(this.nome);
            }
        }
        return cliente;
    }
}
