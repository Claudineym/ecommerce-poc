package br.com.ecommerce.business.domain.entity;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DescontoMonetarioTest {

    @Test
    public void deveAplicarDesconto(){
        BigDecimal vlrDesconto = BigDecimal.ONE;
        BigDecimal vlrProduto = BigDecimal.TEN;
        BigDecimal vlrComDesconto = new BigDecimal("9");
        DescontoMonetario descontoMonetario = new DescontoMonetario(vlrDesconto);
        BigDecimal valordoProdutoComDesconto = descontoMonetario.aplicarDesconto(vlrProduto);
        assertEquals(vlrComDesconto, valordoProdutoComDesconto);
    }
}
