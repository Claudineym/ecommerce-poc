package br.com.ecommerce.business.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
public class ItemCarrinho {
    private String id;
    private Produto produto;
    private Integer quantidade;

    public ItemCarrinho(Produto produto, Integer quantidade) {
        this.id = UUID.randomUUID().toString();
        this.produto = produto;
        this.quantidade = quantidade;
    }

}
