package br.com.ecommerce.business.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vendedor extends Pessoa {

    public Vendedor(String nome, Sexo sexo, Date dtNascimento, String celular, String email, Set<Endereco> enderecos) {
        super(UUID.randomUUID().toString(), nome, sexo, dtNascimento, celular, email, enderecos);
    }
}
