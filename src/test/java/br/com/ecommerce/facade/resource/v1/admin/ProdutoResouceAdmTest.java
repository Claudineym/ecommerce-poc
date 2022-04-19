package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.repository.PagingAndSortingProdutoRepository;
import br.com.ecommerce.business.domain.repository.ProdutoRepository;
import br.com.ecommerce.business.domain.service.ProdutoService;
import br.com.ecommerce.business.domain.service.helper.ProdutoServiceHelper;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.common.resource.ServiceResponse;
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
public class ProdutoResouceAdmTest extends ProdutoResourceAdmBaseTest{

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProdutoRepository repository;
    @MockBean
    private ProdutoService service;
    @MockBean
    private ProdutoServiceHelper helper;
    @MockBean
    private UtilServiceHelper utilHelper;
    @MockBean
    private PagingAndSortingProdutoRepository pagingAndSortingProdutoRepository;

    @Test
    @DisplayName("Teste consutar produto com sucesso")
    void deveConsutar_produto_comSucesso() throws Exception {
        Produto produto = gerarProduto();
        when(repository.findByNome(produto.getNome())).thenReturn(Optional.ofNullable(produto));
        when(helper.buildResult(any())).thenReturn(gerarProdutoResponse());

        mockMvc.perform(get(PATH+"/"+produto.getNome()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(produto.getNome())));
    }

    @Test
    @DisplayName("Teste consutar produto não encontrado")
    void deveConsutar_produto_nao_encontrado() throws Exception {
        String nome = "teste";
        mockMvc.perform(get(PATH+"/"+nome))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagens[0].descricao", is(nome+" não encontrada.")));
    }

    @Test
    @DisplayName("Teste para trazer todos Produtos")
    void deveTrazerTodosProdutos() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria(10, 1);
        Specification<Produto> criteria = new ProdutoServiceHelper().getCriteria(searchCriteria);
        Set<String> sortBy = Set.of("idProduto.desc");
        when(helper.getCriteria(searchCriteria)).thenReturn(criteria);
        PageRequest paging = PageRequest.of(
                searchCriteria.getOffset(),
                searchCriteria.getLimit(),
                Sort.by(new ArrayList<>(getSortedBy(sortBy))));
        when(utilHelper.buildPaging(searchCriteria, sortBy)).thenReturn(paging);

        Page<Produto> pages = Mockito.mock(Page.class);
        pages = pages();
        when(this.pagingAndSortingProdutoRepository.findAll()).thenReturn(pages);
        doReturn(pages()).when(service).listar(any(), any(), any());
        Page<ProdutoResponse> docs = new ProdutoServiceHelper().toProdutoResponse(pages);
        doReturn(docs).when(helper).toProdutoResponse(any());

        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste Criar Produto com Sucesso")
    void deveCriar_produto_comSucesso() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        final String body =
                objectMapper.writeValueAsString(gerarClienteRequest());

        when(service.criar(any())).thenReturn(gerarProduto());

        when(helper.buildResult(any())).thenReturn(gerarProdutoResponse());

        mockMvc
                .perform(
                        post(PATH)
                                .header(AUTH, BEARER_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(gerarProdutoResponse().getNome())));;
    }

    @Test
    @DisplayName("Teste com erro na validação de entrada de Produto")
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
    @DisplayName("Teste excluir Produto com sucesso")
    void deveExcluirProduto_comSucesso() throws  Exception {
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        serviceResponse.setResult(true);
        Produto produto = gerarProduto();
        when(repository.findById(produto.getIdProduto())).thenReturn(Optional.ofNullable(produto));
        Mockito.doNothing().when(service).excluir(produto);

        mockMvc.perform(delete(PATH+"/"+produto.getIdProduto())
                        .header(AUTH, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)));

    }
}
