package br.com.ecommerce.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa {

    @Id
    private String id;
    private String nome;
    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="CLIENTE_ENDERECO",
            joinColumns={@JoinColumn(name = "CLIENTE_ID")},
            inverseJoinColumns={@JoinColumn(name = "ENDERECO_ID")})
    private Set<Endereco> enderecos;
}
