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
public class Cliente {

    @Id
    private String id;
    private String nome;
    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="ENDERECOS",
            joinColumns={@JoinColumn(name = "CLIENTE_ID")},
            inverseJoinColumns={@JoinColumn(name = "ENDERECO_ID")})
    private Set<Endereco> enderecos;

    public Cliente(String nome, Sexo sexo, Date dtNascimento, String celular, String email, Set<Endereco> enderecos) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sexo = sexo;
        this.dtNascimento = dtNascimento;
        this.celular = celular;
        this.email = email;
        this.enderecos = enderecos;
    }

    public Vendedor toVendedor(){
        return new Vendedor(UUID.randomUUID().toString(), this.nome, this.sexo, this.dtNascimento,
                                this.celular, this.email, this.enderecos);
    }
}
