package br.com.ecommerce.business.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DescontoPorcentagem implements DescontoStrategy{

    private BigDecimal vlrDesconto;

    public DescontoPorcentagem(BigDecimal vlrDesconto){
        this.vlrDesconto = vlrDesconto;
    }

    @Override
    public BigDecimal aplicarDesconto(BigDecimal vlrProduto) {
        return vlrProduto.subtract(vlrProduto.multiply(vlrDesconto).divide(BigDecimal.valueOf(100)))
                                                                    .setScale(2, RoundingMode.HALF_UP);
    }
}
