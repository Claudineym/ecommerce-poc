package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.business.domain.repository.ClienteRepository;
import br.com.ecommerce.business.domain.repository.VendedorRepository;
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
public class ClienteServiceTest {

    @InjectMocks private ClienteService service;
    @Mock
    private ClienteRepository repository;
    @Mock private VendedorRepository vendedorRepository;

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(service);
    }

    @Test
    @DisplayName("Service - transformar cliente em vendedor")
    void transformarEmVendedor_ComSucesso() {
        Vendedor vendedor = new Vendedor();
        Cliente cliente = new Cliente();
        when(vendedorRepository.save(any())).thenReturn(vendedor);
        assertThat(service.transformarEmVendedor(cliente));
    }
}
