package br.com.ecommerce.business.domain.entity;

import java.math.BigDecimal;

public interface DescontoStrategy {
    BigDecimal aplicarDesconto(BigDecimal vlrProduto);
}
