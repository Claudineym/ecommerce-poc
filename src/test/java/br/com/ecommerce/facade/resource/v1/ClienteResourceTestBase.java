package br.com.ecommerce.facade.resource.v1;

import br.com.ecommerce.business.domain.entity.Estado;
import br.com.ecommerce.business.domain.entity.Sexo;
import br.com.ecommerce.inbound.dto.PessoaRequest;
import br.com.ecommerce.inbound.dto.EnderecoRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Set;

@Slf4j
public class ClienteResourceTestBase {

    public static final String AUTH = "Authorization";
    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJpcCI6IjE5Mi4xNjguMC4xIiwicm9sZXMiOiIifQ.0luAj0haGR1yEb0SVsRbm_in6z_igIkbnee_znofuyw";
    public static final String PATH = "/v1/clientes/";
    public static final String ID_REQUESICAO = "0d25b4ed-f641-4220-8365-7ad4add4ea63";

    protected PessoaRequest gerarClienteRequest() {
        return PessoaRequest.builder()
                    .nome("Luiz da Silva")
                    .celular("11988252536")
                    .email("lsilva@gmail.com")
                    .dtNascimento(new Date())
                    .sexo(Sexo.FEMININO)
                    .enderecos(Set.of(EnderecoRequest
                            .builder()
                            .endereco("Rua Fulano")
                            .bairro("Centro")
                            .cep("11740123")
                            .cidade("São Paulo")
                            .estado(Estado.SAO_PAULO)
                            .build()))
                    .build();
    }

}
