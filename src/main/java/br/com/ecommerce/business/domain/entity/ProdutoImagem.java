package br.com.ecommerce.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProdutoImagem {

    @Id
    private String idProdutoImagem;
    private String pathImagem;
}
