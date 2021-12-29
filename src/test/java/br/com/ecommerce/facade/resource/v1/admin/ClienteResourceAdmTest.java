package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.service.ClienteService;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.ClienteSearchCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private ClienteService clienteService;

    @Test
    @DisplayName("Teste consutar cliente com sucesso")
    void deveConsutar_cliente_comSucesso() throws Exception {
        given(clienteService.consultar("Fulano01 da Silva")).willReturn(gerarClienteResponse());
        mockMvc.perform(get(PATH+"/"+gerarClienteResponse().getResult().getNome()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.nome", is(gerarClienteResponse().getResult().getNome())));
    }

    @Test
    @DisplayName("Teste consutar cliente não encontrado")
    void deveConsutar_cliente_nao_encontrado() throws Exception {
        String nomeCliente = "teste";
        given(clienteService.consultar(nomeCliente)).willReturn(gerarClienteNaoEncontradoResponse(nomeCliente));
        mockMvc.perform(get(PATH+"/"+nomeCliente))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagens[0].descricao", is(nomeCliente+" não encontrada.")));
    }

    @Test
    @DisplayName("Teste para trazer todos Clientes")
    void deveTrazerTodosClientes() throws Exception {
        given(clienteService.listar(ClienteSearchCriteria.builder().offset(0).limit(10).build(), Set.of("idCliente.desc"))).willReturn(listar());
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
        ServiceResponse<ClienteResponse> cliente = gerarClienteResponse();
        given(clienteService.consultar(cliente.getResult().getNome())).willReturn(gerarClienteResponse());
        when(clienteService.excluir(cliente.getResult().getId())).thenReturn(serviceResponse);

        mockMvc.perform(delete(PATH+"/"+cliente.getResult().getId())
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
        ServiceResponse<ClienteResponse> cliente = gerarClienteResponse();
        given(clienteService.consultar(cliente.getResult().getNome())).willReturn(gerarClienteResponse());
        when(clienteService.excluir(cliente.getResult().getId())).thenReturn(serviceResponse);

        mockMvc.perform(delete(PATH+"/"+cliente.getResult().getId())
                        .header(AUTH, BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(false)));

    }
}
