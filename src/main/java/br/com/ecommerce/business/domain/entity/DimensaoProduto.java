package br.com.ecommerce.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DimensaoProduto {
    @Id
    private String idDimensaoProduto;
    private Double largura;
    private Double altura;
    private Double comprimento;
    private Double peso;
}
