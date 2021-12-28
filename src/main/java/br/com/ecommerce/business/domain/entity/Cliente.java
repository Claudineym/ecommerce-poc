package br.com.ecommerce.business.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = -861235869034747900L;
    @Id
    private String idCliente;
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
