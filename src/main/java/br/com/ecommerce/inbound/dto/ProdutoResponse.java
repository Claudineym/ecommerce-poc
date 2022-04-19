package br.com.ecommerce.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {
    private String idProduto;
    private String nome;
    private String descProduto;
    private String tags;
}
