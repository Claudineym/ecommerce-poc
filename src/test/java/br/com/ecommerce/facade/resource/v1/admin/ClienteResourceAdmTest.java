package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelper;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import br.com.ecommerce.inbound.dto.VendedorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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


@WebMvcTest
@AutoConfigureMockMvc
public class ClienteResourceAdmTest extends ClienteResourceAdmTestBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteRepository clienteRepository;
    @MockBean
    private ClienteService clienteService;
    @MockBean
    private ClienteServiceHelper helper;

    @Test
    @DisplayName("Teste consutar cliente com sucesso")
    void deveConsutar_cliente_comSucesso() throws Exception {
        mockMvc.perform(get(PATH+"/"+gerarCliente().getNome()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(gerarCliente().getNome())));
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
        ClienteSearchCriteria searchCriteria = ClienteSearchCriteria.builder().offset(0).limit(10).build();
        Specification<Cliente> criteria = helper.getCriteria(searchCriteria);

        given(clienteService.listar(criteria, searchCriteria, Set.of("idCliente.desc"))).willReturn(listar());
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
        mockMvc
                .perform(
                        post(PATH)
                                .header(AUTH, BEARER_TOKEN)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(body))
                .andDo(print())
                .andExpect(status().isOk());

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
        Mockito.doNothing().when(clienteService).excluir(cliente);

        mockMvc.perform(delete(PATH+"/"+cliente)
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
        Mockito.doNothing().when(clienteService).excluir(any());

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
        when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.ofNullable(cliente));

        given(clienteService.transformarEmVendedor(cliente)).willReturn(cliente.toVendedor());

        mockMvc.perform(put(PATH+"/"+cliente.getId())
                            .header(AUTH, BEARER_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
