package br.com.ecommerce.business.domain.entity;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class DescontoPorcentagemTest {


    @Test
    public void deveAplicarDesconto(){
        BigDecimal vlrDesconto = new BigDecimal("5.5");
        BigDecimal vlrProduto = new BigDecimal("255.89");
        BigDecimal vlrComDesconto = new BigDecimal("241.82");

        DescontoPorcentagem descontoPorcentagem = new DescontoPorcentagem(vlrDesconto);
        BigDecimal valordoProdutoComDesconto = descontoPorcentagem.aplicarDesconto(vlrProduto);

        assertEquals(vlrComDesconto, valordoProdutoComDesconto);
    }
}
