package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.repository.ProdutoRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest extends EcommerceTestBase {

    @InjectMocks
    private ProdutoService service;
    @Mock
    private ProdutoRepository repository;

    @BeforeEach
    public void init() throws JsonProcessingException {
        MockitoAnnotations.openMocks(service);
    }

    @Test
    @DisplayName("Service - criar produto com sucesso")
    void criarCliente_comSucesso(){
        Produto produto = gerarProduto();
        when(repository.save(any())).thenReturn(produto);
        Produto retorno = service.criar(produto);
        assertThat(produto.getNome().equals(retorno.getNome()));
    }
}
