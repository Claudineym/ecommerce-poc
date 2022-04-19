package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.PagingAndSortingClienteRepository;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.SearchCriteria;
import br.com.ecommerce.inbound.dto.VendedorResponse;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
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

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteResourceAdmTest extends ClienteResourceAdmBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteRepository repository;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private PagingAndSortingClienteRepository pagingAndSortingClienteRepository;
    @MockBean
    private ClienteServiceHelper helper;
    @MockBean
    private UtilServiceHelper utilHelper;

    @Test
    @DisplayName("Teste consutar cliente com sucesso")
    void deveConsutar_cliente_comSucesso() throws Exception {
        Cliente cliente = gerarCliente();
        when(repository.findByNome(cliente.getNome())).thenReturn(Optional.ofNullable(cliente));
        when(helper.toClienteResponse(any(), any())).thenReturn(gerarClienteResponse());

        mockMvc.perform(get(PATH+"/"+cliente.getNome()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(cliente.getNome())));
    }

    @Test
    @DisplayName("Teste consutar cliente não encontrado")
    void deveConsutar_cliente_nao_encontrado() throws Exception {
        String nomeCliente = "teste";
        mockMvc.perform(get(PATH+"/"+nomeCliente))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagens[0].descricao", is(nomeCliente+" não encontrada.")));
    }

    @Test
    @DisplayName("Teste para trazer todos Clientes")
    void deveTrazerTodosClientes() throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria(10, 1);
        Specification<Cliente> criteria = new ClienteServiceHelper().getCriteria(searchCriteria);
        Set<String> sortBy = Set.of("idCliente.desc");
        when(helper.getCriteria(searchCriteria)).thenReturn(criteria);
        PageRequest paging = PageRequest.of(
                searchCriteria.getOffset(),
                searchCriteria.getLimit(),
                Sort.by(new ArrayList<>(getSortedBy(sortBy))));
        when(utilHelper.buildPaging(searchCriteria, sortBy)).thenReturn(paging);

        Page<Cliente> pages = Mockito.mock(Page.class);
        pages = pages();
        when(this.pagingAndSortingClienteRepository.findAll()).thenReturn(pages);
        doReturn(pages()).when(clienteService).listar(any(), any(), any());
        Page<ClienteResultResponse> docs = new ClienteServiceHelper().toClienteResponse(pages);
        doReturn(docs).when(helper).toClienteResponse(any());

        mockMvc.perform(get(PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste Criar Cliente com Sucesso")
    void deveCriar_cliente_comSucesso() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        final String body =
                objectMapper.writeValueAsString(gerarClienteRequest());

        when(clienteService.criar(any())).thenReturn(gerarCliente());

        when(helper.toClienteResponse(any(), any())).thenReturn(gerarClienteResponse());

        mockMvc
                .perform(
                        post(PATH)
                                .header(AUTH, BEARER_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(gerarClienteResponse().getNome())));;

    }

    @Test
    @DisplayName("Teste com erro na validação de entrada de Cliente")
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
    @DisplayName("Teste excluir Cliente com sucesso")
    void deveExcluirCliente_comSucesso() throws  Exception {
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        serviceResponse.setResult(true);
        Cliente cliente = gerarCliente();
        when(repository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));
        Mockito.doNothing().when(clienteService).excluir(cliente);

        mockMvc.perform(delete(PATH+"/"+cliente.getId())
                        .header(AUTH, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(true)));

    }

    @Test
    @DisplayName("Teste excluir Cliente com Erro")
    void deveExcluirCliente_comErro() throws  Exception {
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();
        serviceResponse.setResult(false);
        Cliente cliente = gerarCliente();
        when(repository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));
        Mockito.doNothing().when(clienteService).excluir(cliente);

        mockMvc.perform(delete(PATH+"/"+cliente)
                        .header(AUTH, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(false)));

    }

    @Test
    @DisplayName("Teste transformar Cliente em Vendedor")
    void deveTransformarClienteEmVendedor_comSucesso() throws Exception {
        final ServiceResponse<VendedorResponse> serviceResponse = new ServiceResponse<>();
        Cliente cliente = gerarCliente();
        when(repository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));

        given(clienteService.transformarEmVendedor(cliente)).willReturn(cliente.toVendedor());

        mockMvc.perform(put(PATH+"/"+cliente.getId())
                            .header(AUTH, BEARER_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
