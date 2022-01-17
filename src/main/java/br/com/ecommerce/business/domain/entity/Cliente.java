package br.com.ecommerce.business.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Cliente extends Pessoa {
    public Cliente(String nome, Sexo sexo, Date dtNascimento, String celular, String email, Set<Endereco> enderecos) {
        super(UUID.randomUUID().toString(), nome, sexo, dtNascimento, celular, email, enderecos);
    }
}
