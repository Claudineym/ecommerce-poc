package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.entity.ProdutoImagem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class ProdutoEditarRequest {
    @JsonProperty private String nome;
    @JsonProperty private String descProduto;
    @JsonProperty private Integer quantidade;
    @JsonProperty private BigDecimal valorUnitario;
    @JsonProperty private BigDecimal valorPromocial;
    @JsonProperty private String tags;
    @JsonProperty private CategoriaProduto categoriaProduto;
    @JsonProperty private Set<ProdutoImagem> produtoImagens;

    public Produto updateProduto(Produto produto){
        if(Objects.nonNull(this.nome) ){
            if(!this.nome.equals(produto.getNome())){
                produto.setNome(this.nome);
            }
        }
        if(Objects.nonNull(this.descProduto)){
            if(!this.descProduto.equals(produto.getDescProduto())){
                produto.setDescProduto(this.descProduto);
            }
        }
        if(Objects.nonNull(this.quantidade)){
            if(!this.quantidade.equals(produto.getQuantidade())){
                produto.setQuantidade(this.quantidade);
            }
        }
        if(Objects.nonNull(this.valorUnitario)){
            if(!this.valorUnitario.equals(produto.getValorUnitario())){
                produto.setValorUnitario(this.valorUnitario);
            }
        }
        if(Objects.nonNull(this.valorPromocial)){
            if(!this.valorPromocial.equals(produto.getValorPromocial())){
                produto.setValorPromocial(this.valorPromocial);
            }
        }
        if(Objects.nonNull(this.tags)){
            if(!this.tags.equals(produto.getTags())){
                produto.setTags(this.tags);
            }
        }
        if(Objects.nonNull(this.categoriaProduto)){
            if(!this.categoriaProduto.equals(produto.getCategoriaProduto())){
                produto.setCategoriaProduto(this.categoriaProduto);
            }
        }
        if(Objects.nonNull(this.produtoImagens)){
            if(!this.produtoImagens.equals(produto.getProdutoImagens())){
                produto.setProdutoImagens(this.produtoImagens);
            }
        }
        return produto;
    }
}
