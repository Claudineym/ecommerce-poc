package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Endereco;
import br.com.ecommerce.business.domain.entity.Estado;
import br.com.ecommerce.business.domain.entity.Sexo;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelperTest;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.EnderecoRequest;
import br.com.ecommerce.outbound.dto.PessoaResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;

@Slf4j
public class ClienteResourceAdmTestBaseTest extends ClienteServiceHelperTest {

    public static final String AUTH = "Authorization";
    public static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJpcCI6IjE5Mi4xNjguMC4xIiwicm9sZXMiOiIifQ.0luAj0haGR1yEb0SVsRbm_in6z_igIkbnee_znofuyw";
    public static final String PATH = "/v1/admin/clientes";
    public static final String ID_REQUESICAO = "0d25b4ed-f641-4220-8365-7ad4add4ea63";

    private List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        listar();
    }

    protected Page<Cliente> listar(){
        ServicePageableResponse<List<PessoaResultResponse>> response =
                new ServicePageableResponse<>();
        Page<Cliente> pages = new PageImpl(gerarListaDeClientes());
        return pages;
    }

    protected Cliente gerarCliente() {
        return new Cliente("Fulano01 da Silva", Sexo.MASCULINO, new Date(), "11985263236", "fulano01@gmail.com",
                (new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build()))));
    }

    protected List<Cliente> gerarListaDeClientes(){
        this.clientes = new ArrayList<>();
        this.clientes.add(new Cliente("Fulano01 da Silva", Sexo.MASCULINO, new Date(), "11985263236", "fulano02@bol.com.br",
                (new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                        .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build())))));
        this.clientes.add(new Cliente("Fulano02 de Oliveira", Sexo.MASCULINO, new Date(), "11985263236", "fulano01@gmail.com",
                (new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 02").bairro("Tal Ribeirão")
                        .cep("55250000").cidade("Ribeirão Preto").estado(Estado.SAO_PAULO).build())))));
        this.clientes.add(new Cliente("Fulano03 Duarte", Sexo.MASCULINO, new Date(), "31985863698", "fulano03@uai.com.br",
                (new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 03").bairro("Centro")
                        .cep("38250000").cidade("Belo Horizonte").estado(Estado.SAO_PAULO).build())))));
        return this.clientes;
    }

    protected ClienteRequest gerarClienteRequest() {
        return ClienteRequest.builder()
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

    protected ServiceResponse gerarClienteNaoEncontradoResponse(String nomeCliente){
        final ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nomeCliente));
        return serviceResponse;
    }
}
