package br.com.ecommerce.business.domain.entity;

import br.com.ecommerce.common.EcommerceTestBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ProdutoTest extends EcommerceTestBase {

    private Produto produto1;
    private Produto produto2;

    @Before
    public void setUp() {
        this.produto1 = new Produto(VALOR_TEXTO_TESTE_GENERICO);
        this.produto2 = new Produto(VALOR_TEXTO_TESTE_GENERICO);
    }

    @Test
    public void testar_equals() {
        assertEquals(Boolean.TRUE, this.produto1.equals(this.produto2));
    }

    @Test
    public void testar_hashcode() {
        assertEquals(Boolean.TRUE, this.produto1.hashCode() == this.produto2.hashCode());
    }

    @Test
    public void deveRetornarOValorDoProdutoSemDesconto(){
        BigDecimal vlrProduto = BigDecimal.TEN;
        Produto produtoSemDesconto = new Produto(vlrProduto);
        assertEquals(vlrProduto, produtoSemDesconto.getValorUnitario());
        assertEquals(vlrProduto, produtoSemDesconto.getValorDeVendaDoProduto());
    }

    @Test
    public void deveAplicarUmDescontoMonetarioAoProduto() {
        BigDecimal vlrProduto = BigDecimal.TEN;
        BigDecimal vlrDesconto = BigDecimal.ONE;
        BigDecimal vlrDeVendaComDesconto = new BigDecimal("9");

        Produto produtoComDesconto = new Produto(vlrProduto);

        DescontoStrategy desconto = new DescontoMonetario(vlrDesconto);
        produtoComDesconto.aplicarDesconto(desconto);

        assertEquals(vlrDeVendaComDesconto, produtoComDesconto.getValorPromocial());
        assertEquals(vlrProduto, produtoComDesconto.getValorUnitario());
        assertEquals(vlrDeVendaComDesconto, produtoComDesconto.getValorDeVendaDoProduto());
    }

    @Test
    public void deveAplicarUmDescontoPorcentagemAoProduto() {
        BigDecimal vlrProduto = BigDecimal.TEN;
        BigDecimal vlrDesconto = BigDecimal.TEN;
        BigDecimal vlrDeVendaComDesconto = new BigDecimal("9.00");

        Produto produtoComDesconto = new Produto(vlrProduto);

        DescontoStrategy desconto = new DescontoPorcentagem(vlrDesconto);
        produtoComDesconto.aplicarDesconto(desconto);

        assertEquals(vlrDeVendaComDesconto, produtoComDesconto.getValorPromocial());
        assertEquals(vlrProduto, produtoComDesconto.getValorUnitario());
        assertEquals(vlrDeVendaComDesconto, produtoComDesconto.getValorDeVendaDoProduto());
    }

    @Test
    public void deveAplicarUmDescontoAoProduto(){
        BigDecimal vlrProduto = BigDecimal.TEN;

        BigDecimal vlrComDesconto = new BigDecimal("9.00");
        Produto produtoComDesconto = new Produto(vlrProduto);

        DescontoStrategy desconto = Mockito.mock(DescontoStrategy.class);
        Mockito.when(desconto.aplicarDesconto(vlrProduto)).thenReturn(vlrComDesconto);
        produtoComDesconto.aplicarDesconto(desconto);

        assertEquals(vlrComDesconto, produtoComDesconto.getValorPromocial());
    }


}
