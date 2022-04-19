package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.*;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelperTest;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
public class CategoriaProdutoResourceAdmBaseTest {

    public static final String AUTH = "Authorization";
    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJpcCI6IjE5Mi4xNjguMC4xIiwicm9sZXMiOiIifQ.0luAj0haGR1yEb0SVsRbm_in6z_igIkbnee_znofuyw";
    public static final String PATH = "/v1/admin/categoria-produtos";
    public static final String ID_REQUESICAO = "0d25b4ed-f641-4220-8365-7ad4add4ea63";

    private List<CategoriaProduto> categoriasProdutos;

    protected CategoriaProduto gerarCategoriaProduto() {
        return new CategoriaProduto("categoriaProduto001", "desc categoria produto 001");
    }

    protected List<CategoriaProduto> gerarListaDeProdutos(){
        this.categoriasProdutos = new ArrayList<>();
        this.categoriasProdutos.add(new CategoriaProduto("CategoriaProduto 001",
            "desc categoria produto 001"));
        this.categoriasProdutos.add(new CategoriaProduto("CategoriaProduto 002",
                "desc categoria produto 002"));
        return categoriasProdutos;
    }

    protected CategoriaProdutoRequest gerarCategoriaProdutoRequest() {
        return CategoriaProdutoRequest.builder().descCategoriaProduto("desc categoria produto 001").build();
    }

    protected CategoriaProdutoResponse gerarCategoriaProdutoResponse() {
        CategoriaProduto produto = gerarCategoriaProduto();
        return CategoriaProdutoResponse.builder()
                .idCategoriaProduto(produto.getIdCategoriaProduto())
                .descCategoriaProduto(produto.getDescCategoriaProduto())
                .build();
    }

    protected ServiceResponse gerarNaoEncontradoResponse(String nome){
        final ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nome));
        return serviceResponse;
    }
}
