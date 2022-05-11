package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.*;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelperTest;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.EnderecoRequest;
import br.com.ecommerce.inbound.dto.ProdutoRequest;
import br.com.ecommerce.inbound.dto.ProdutoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class ProdutoResourceAdmBaseTest extends ClienteServiceHelperTest {

    public static final String AUTH = "Authorization";
    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJpcCI6IjE5Mi4xNjguMC4xIiwicm9sZXMiOiIifQ.0luAj0haGR1yEb0SVsRbm_in6z_igIkbnee_znofuyw";
    public static final String PATH = "/v1/admin/produtos";
    public static final String ID_REQUESICAO = "0d25b4ed-f641-4220-8365-7ad4add4ea63";

    private List<Cliente> clientes;
    private List<Produto> produtos;

    protected Page<Produto> pages(){
        Page<Produto> pages = new PageImpl(gerarListaDeProdutos());
        return pages;
    }

    protected Produto gerarProduto() {
        return new Produto("Produto 001", "desc produto 001",
                BigDecimal.valueOf(100.85), BigDecimal.valueOf(99.9), "TAG1",
                new CategoriaProduto("Eletrônico"), Set.of(new ProdutoImagem()));
    }

    protected List<Produto> gerarListaDeProdutos(){
        this.produtos = new ArrayList<>();
        this.produtos.add(new Produto("Produto 001", "desc produto 001",
                BigDecimal.valueOf(100.85), BigDecimal.valueOf(99.9), "TAG1",
                new CategoriaProduto("Eletrônico"), Set.of(new ProdutoImagem())));
        this.produtos.add(new Produto("Produto 002", "desc produto 002",
                BigDecimal.valueOf(980.85), BigDecimal.valueOf(965.25), "TAG2",
                new CategoriaProduto("Papelaria"), Set.of(new ProdutoImagem())));
        this.produtos.add(new Produto("Produto 003", "desc produto 003",
                BigDecimal.valueOf(5.25), BigDecimal.valueOf(5.20), "TAG3",
                new CategoriaProduto("Cama e Mesa"), Set.of(new ProdutoImagem())));
        return produtos;
    }

    protected ProdutoRequest gerarProdutoRequest() {
        return ProdutoRequest.builder()
                .descProduto("desc produto")
                .categoriaProduto(new CategoriaProduto("desc categoria produto"))
                .valorPromocial(BigDecimal.valueOf(10.99))
                .valorUnitario(BigDecimal.valueOf(11.00))
                .tags("TAGS").build();
    }

    protected ProdutoResponse gerarProdutoResponse() {
        Produto produto = gerarProduto();
        return ProdutoResponse.builder()
                .idProduto(produto.getIdProduto())
                .nome(produto.getNome())
                .descProduto(produto.getDescProduto())
                .tags(produto.getTags()).build();
    }

    protected ServiceResponse gerarNaoEncontradoResponse(String nome){
        final ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nome));
        return serviceResponse;
    }
}
