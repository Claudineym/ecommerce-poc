package br.com.ecommerce.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Frete {
    @Id
    private String idFrete;
    private BigDecimal vlrFrete;
}
