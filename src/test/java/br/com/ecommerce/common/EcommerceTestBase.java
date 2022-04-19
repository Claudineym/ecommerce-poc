package br.com.ecommerce.common;

import br.com.ecommerce.business.domain.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class EcommerceTestBase {
    protected static final String VALOR_TEXTO_TESTE_GENERICO = "Teste";
    protected static final String VALOR_NOME = "Teste";

    protected Cliente gerarCliente() {
        return new Cliente("Fulano01 da Silva", Sexo.MASCULINO, new Date(), "11985263236", "fulano01@gmail.com",
                (new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                        .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build()))));
    }

    protected Produto gerarProduto() {
        return new Produto("Produto 001", "desc produto 001", 10,
                BigDecimal.valueOf(100.85), BigDecimal.valueOf(99.9), "TAG1",
                new CategoriaProduto("Eletrônico"), Set.of(new ProdutoImagem()));
    }
}
