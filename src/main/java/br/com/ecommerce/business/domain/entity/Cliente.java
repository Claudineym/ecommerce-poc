package br.com.ecommerce.business.domain.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cliente implements Serializable {
    private static final long serialVersionUID = -861235869034747900L;
    @Id
    @GeneratedValue
    @Column( columnDefinition = "uuid", updatable = false)
    private UUID idCliente = UUID.randomUUID();
    private String nome;
    @Enumerated(EnumType.ORDINAL)
    private Sexo sexo;
    private Date dtNascimento;
    private String celular;
    private String email;
}
