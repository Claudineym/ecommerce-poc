package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.VendedorRepository;
import br.com.ecommerce.common.EcommerceTestBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest extends EcommerceTestBase {

    @InjectMocks
    private ClienteService service;
    @Mock
    private ClienteRepository repository;
    @Mock
    private VendedorRepository vendedorRepository;

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(service);
    }

    @Test
    @DisplayName("Service - transformar cliente em vendedor")
    void transformarEmVendedor_ComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome(VALOR_NOME);
        Vendedor vendedor = new Vendedor();
        when(vendedorRepository.save(any())).thenReturn(vendedor);
        Vendedor vendedorRetorno = service.transformarEmVendedor(cliente);
        assertThat(cliente.getNome().equals(vendedorRetorno.getNome()));
    }

    @Test
    @DisplayName("Service - criar cliente com sucesso")
    void criarCliente_comSucesso(){
        Cliente cliente = gerarCliente();
        when(repository.save(any())).thenReturn(cliente);
        Cliente retorno = service.criar(cliente);
        assertThat(cliente.getNome().equals(retorno.getNome()));
    }
}