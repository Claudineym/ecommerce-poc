package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.entity.ProdutoImagem;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
public class ProdutoRequest {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    private String descProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorPromocial;
    private String tags;
    private CategoriaProduto categoriaProduto;
    private Set<ProdutoImagem> produtoImagens;

    public Produto toProduto(){
        return new Produto(this.nome, this.descProduto, this.quantidade, this.valorUnitario,
                this.valorPromocial, this.tags, this.categoriaProduto, this.produtoImagens);
    }
}
