package br.com.ecommerce.business.domain.entity;

import br.com.ecommerce.common.EcommerceTestBase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoTest extends EcommerceTestBase {

    private Produto produto1;
    private Produto produto2;

    @Before
    public void setUp() {
        this.produto1 = new Produto(VALOR_TEXTO_TESTE_GENERICO);
        this.produto2 = new Produto(VALOR_TEXTO_TESTE_GENERICO);
    }

    @Test
    public void testar_equals() {
        assertEquals(Boolean.TRUE, this.produto1.equals(this.produto2));
    }

    @Test
    public void testar_hashcode() {
        assertEquals(Boolean.TRUE, this.produto1.hashCode() == this.produto2.hashCode());
    }
}
