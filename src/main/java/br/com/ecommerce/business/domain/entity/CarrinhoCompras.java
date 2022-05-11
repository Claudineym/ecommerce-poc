package br.com.ecommerce.business.domain.entity;

import java.util.*;

public class CarrinhoCompras {

    private String id;
    private List<ItemCarrinho> itens = new LinkedList<>();
    private Cliente cliente;

    public void adicionarItemNoCarrinho( ItemCarrinho item){
        itens.add(item);
    }

    public void removerItemNoCarrinho(ItemCarrinho item) {
        itens.remove(item);
    }

    public Integer getQuantidadeItens(){
        return itens.size();
    }
}
