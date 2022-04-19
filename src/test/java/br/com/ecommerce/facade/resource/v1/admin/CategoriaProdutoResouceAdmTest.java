package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.repository.CategoriaProdutoRepository;
import br.com.ecommerce.business.domain.repository.PagingAndSortingProdutoRepository;
import br.com.ecommerce.business.domain.repository.ProdutoRepository;
import br.com.ecommerce.business.domain.service.CategoriaProdutoService;
import br.com.ecommerce.business.domain.service.ProdutoService;
import br.com.ecommerce.business.domain.service.helper.CategoriaProdutoServiceHelper;
import br.com.ecommerce.business.domain.service.helper.ProdutoServiceHelper;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.CategoriaProdutoResponse;
import br.com.ecommerce.inbound.dto.ProdutoResponse;
import br.com.ecommerce.inbound.dto.SearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaProdutoResouceAdmTest extends CategoriaProdutoResourceAdmBaseTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoriaProdutoRepository repository;
    @MockBean
    private CategoriaProdutoService service;
    @MockBean
    private CategoriaProdutoServiceHelper helper;
    @MockBean
    private UtilServiceHelper utilHelper;

    @Test
    @DisplayName("Teste consutar categoria de produto com sucesso")
    void deveConsutar_categoria_produto_comSucesso() throws Exception {
        CategoriaProduto produto = gerarCategoriaProduto();
        when(repository.findByDescCategoriaProduto(produto.getDescCategoriaProduto()))
                .thenReturn(Optional.ofNullable(produto));
        when(helper.buildResult(any())).thenReturn(gerarCategoriaProdutoResponse());

        mockMvc.perform(get(PATH+"/"+produto.getDescCategoriaProduto()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.descCategoriaProduto", is(produto.getDescCategoriaProduto())));
    }

    @Test
    @DisplayName("Teste consutar categoria de produto não encontrado")
    void deveConsutar_produto_nao_encontrado() throws Exception {
        String nome = "teste";
        mockMvc.perform(get(PATH+"/"+nome))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagens[0].descricao", is(nome+" não encontrada.")));
    }

    @Test
    @DisplayName("Teste para trazer todas as Categorias de Produtos")
    void deveTrazerTodosProdutos() throws Exception {
        doReturn(gerarListaDeProdutos()).when(service).listar();
        List<CategoriaProdutoResponse> docs = new CategoriaProdutoServiceHelper()
                            .toCategoriaProdutoResponse(gerarListaDeProdutos());
        doReturn(docs).when(helper).toCategoriaProdutoResponse(any());

        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste Criar Categoria de Produto com Sucesso")
    void deveCriar_categoria_produto_comSucesso() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        final String body =
                objectMapper.writeValueAsString(gerarCategoriaProdutoRequest());

        when(service.criar(any())).thenReturn(gerarCategoriaProduto());

        when(helper.buildResult(any())).thenReturn(gerarCategoriaProdutoResponse());

        mockMvc
                .perform(
                        post(PATH)
                                .header(AUTH, BEARER_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.descCategoriaProduto", is(gerarCategoriaProdutoResponse().getDescCategoriaProduto())));;
    }

    @Test
    @DisplayName("Teste com erro na validação de entrada de Categoria de Produto")
    void deveRetornarBadRequestNaValidacaoDeEntrada() throws Exception {
        String contentRequestWork = "{}";
        mockMvc
                .perform(
                        post(PATH)
                                .header(AUTH, BEARER_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(contentRequestWork))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Teste excluir Categoria de Produto com sucesso")
    void deveExcluirCategoriaProduto_comSucesso() throws  Exception {
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        serviceResponse.setResult(true);
        CategoriaProduto produto = gerarCategoriaProduto();
        when(repository.findById(produto.getIdCategoriaProduto())).thenReturn(Optional.ofNullable(produto));
        Mockito.doNothing().when(service).excluir(produto);

        mockMvc.perform(delete(PATH+"/"+produto.getIdCategoriaProduto())
                        .header(AUTH, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)));

    }
}
