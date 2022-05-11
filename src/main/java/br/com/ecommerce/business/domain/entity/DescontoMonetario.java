package br.com.ecommerce.business.domain.entity;

import java.math.BigDecimal;

public class DescontoMonetario implements DescontoStrategy {

    private BigDecimal vlrDesconto;

    public DescontoMonetario(BigDecimal vlrDesconto) {
        this.vlrDesconto = vlrDesconto;
    }

    @Override
    public BigDecimal aplicarDesconto(BigDecimal vlrProduto) {
        return vlrProduto.subtract(this.vlrDesconto);
    }
}
