package br.com.ecommerce.facade.resource.v1;

import br.com.ecommerce.business.domain.service.ClienteService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
class ClienteResourceTest extends ClienteResourceTestBase {

    private static final String RESOURCE_CONTEXT = "/v1/clientes";
    @Autowired private MockMvc mockMvc;;
    @MockBean
    private ClienteService clienteService;


}
