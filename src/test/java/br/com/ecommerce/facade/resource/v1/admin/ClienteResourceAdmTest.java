package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class ClienteResourceAdmTest extends ClienteResourceAdmTestBase {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("Test Criar Cliente com Sucesso")
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
    @DisplayName("Test Criar Cliente com erro na validação de entrada")
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
}
