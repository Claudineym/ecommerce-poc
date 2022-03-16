package br.com.ecommerce.business.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Vendedor extends Cliente {

    @Id
    private String id;

    public Vendedor(String id, String nome, Sexo sexo, Date dtNascimento, String celular, String email, Set<Endereco> enderecos) {
        super(nome, sexo, dtNascimento, celular, email, enderecos);
        this.id = id;
    }

}
