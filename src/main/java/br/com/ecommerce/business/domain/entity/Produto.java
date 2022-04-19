package br.com.ecommerce.business.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    private String idProduto;
    private String nome;
    private String descProduto;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorPromocial;
    private String tags;

    @ManyToOne
    @JoinColumn(name = "categoria_produto_id_categoria_produto")
    private CategoriaProduto categoriaProduto;

   /* @OneToOne
    @JoinColumn(name = "frete_id_frete")
    private Frete frete;

    @OneToOne
    @JoinColumn(name = "dimensao_produto_id_dimensao_produto")
    private DimensaoProduto dimensaoProduto;

    @OneToOne
    @JoinColumn(name = "dimensao_pacote_id_dimensao_produto")
    private DimensaoProduto dimensaoPacote;*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="IMAGEM",
            joinColumns={@JoinColumn(name = "PRODUTO_ID")},
            inverseJoinColumns={@JoinColumn(name = "IMAGEM_ID")})
    private Set<ProdutoImagem> produtoImagens;


    public Produto(String nome, String descProduto, Integer quantidade, BigDecimal valorUnitario,
                   BigDecimal valorPromocial, String tags, CategoriaProduto categoriaProduto, Set<ProdutoImagem> produtoImagens) {
        this.idProduto = UUID.randomUUID().toString();
        this.nome = nome;
        this.descProduto = descProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorPromocial = valorPromocial;
        this.tags = tags;
        this.categoriaProduto = categoriaProduto;
        this.produtoImagens = produtoImagens;
    }

    public Produto(String nome) {
        this.nome = nome;
    }
}