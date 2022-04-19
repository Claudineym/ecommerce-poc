package br.com.ecommerce.inbound.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoriaProdutoResponse {

    private String idCategoriaProduto;
    private String descCategoriaProduto;

}
