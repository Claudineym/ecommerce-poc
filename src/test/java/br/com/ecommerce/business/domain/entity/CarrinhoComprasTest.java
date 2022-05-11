package br.com.ecommerce.business.domain.entity;

import br.com.ecommerce.common.EcommerceTestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarrinhoComprasTest extends EcommerceTestBase {

    private ItemCarrinho item;
    private CarrinhoCompras carrinho;

    @Before
    public void setup(){
        Integer QTDE = 8;
        carrinho = new CarrinhoCompras();
        Produto produto = this.gerarProduto();
        item = new ItemCarrinho(produto, QTDE);
        carrinho.adicionarItemNoCarrinho(item);
    }

    @Test
    public void deveAdicionarUmItemNoCarrinhoDeCompras_comSucesso(){
        Integer UM = 1;
        assertEquals(UM, carrinho.getQuantidadeItens());
    }

    @Test
    public void deveRemoverUmItemNoCarrinhoDeCompras_comSucesso(){
        Integer ZERO = 0;
        carrinho.removerItemNoCarrinho(item);
        assertEquals(ZERO, carrinho.getQuantidadeItens());
    }

    @Test
    public void deveAdicionarItensNoCarrinhoDeCompras_comSucesso(){
        Integer QTDE = 12;
        CarrinhoCompras carrinho = this.gerarCarrinhoComVariosProdutos();
        assertEquals(QTDE, carrinho.getQuantidadeItens());
    }

    @Test
    public void deveRemover3ItensNoCarrinhoDeCompras_comSucesso(){
        Integer QTDE = 3;
        Produto produto = this.gerarProduto();
        CarrinhoCompras carrinho = new CarrinhoCompras();
        ItemCarrinho item1 = new ItemCarrinho(produto, 10);
        ItemCarrinho item2 = new ItemCarrinho(produto, 2);
        ItemCarrinho item3 = new ItemCarrinho(produto, 5);
        ItemCarrinho item4 = new ItemCarrinho(produto, 8);

        carrinho.adicionarItemNoCarrinho(item1);
        carrinho.adicionarItemNoCarrinho(item2);
        carrinho.adicionarItemNoCarrinho(item3);
        carrinho.adicionarItemNoCarrinho(item4);

        carrinho.removerItemNoCarrinho(item2);

        assertEquals(QTDE, carrinho.getQuantidadeItens());
    }


}
