package br.com.ecommerce.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Endereco;
import br.com.ecommerce.business.domain.entity.Estado;
import br.com.ecommerce.business.domain.entity.Sexo;
import br.com.ecommerce.business.domain.service.helper.ClienteServiceHelperTest;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.ClienteRequest;
import br.com.ecommerce.inbound.dto.ClienteResponse;
import br.com.ecommerce.inbound.dto.EnderecoRequest;
import br.com.ecommerce.inbound.dto.EnderecoResponse;
import br.com.ecommerce.outbound.dto.ClienteResultResponse;
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

    protected ServicePageableResponse<List<ClienteResultResponse>> listar(){
        ServicePageableResponse<List<ClienteResultResponse>> response =
                new ServicePageableResponse<>();
        Page<Cliente> pages = new PageImpl(gerarListaDeClientes());
        var pageMetadata =
                new PageMetadata(
                        pages.getTotalElements(),
                        (long) 0,
                        (long) 10);
        Page<ClienteResultResponse> docs = toClienteResponse(pages);

        response.setMetadata(pageMetadata);
        response.setResult(docs.getContent());
        return response;
    }

    protected Cliente gerarCliente() {
        return Cliente.builder().idCliente("0d25b4ed-f641-4220-8365-7ad4add4ea63cd")
                .nome("Fulano01 da Silva").celular("11985263236").email("fulano01@gmail.com")
                .dtNascimento(new Date()).enderecos(new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                        .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build()))).build();
    }

    protected List<Cliente> gerarListaDeClientes(){
        this.clientes = new ArrayList<>();
        this.clientes.add(Cliente.builder().idCliente("0d25b4ed-f641-4220-8365-7ad4add4ea63cd")
                .nome("Fulano01 da Silva").celular("11985263236").email("fulano01@gmail.com")
                .dtNascimento(new Date()).enderecos(new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                        .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build()))).build());
        this.clientes.add(Cliente.builder().idCliente("0d25b4ed-f641-4220-8365-7ad4add4ea645o")
                .nome("Fulano02 de Oliveira").celular("16985863288").email("fulano02@bol.com.br")
                .dtNascimento(new Date()).enderecos(new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 02").bairro("Tal Ribeirão")
                        .cep("55250000").cidade("Ribeirão Preto").estado(Estado.SAO_PAULO).build()))).build());
        this.clientes.add(Cliente.builder().idCliente("0d25b4ed-f641-4220-8365-58j4add4ea645l")
                .nome("Fulano03 Duarte").celular("31985863698").email("fulano03@uai.com.br")
                .dtNascimento(new Date()).enderecos(new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 03").bairro("Centro")
                        .cep("38250000").cidade("Belo Horizonte").estado(Estado.SAO_PAULO).build()))).build());
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

    protected ServiceResponse<ClienteResponse> gerarClienteResponse(){
        final ServiceResponse<ClienteResponse> serviceResponse = new ServiceResponse<>();

        Cliente cliente = Cliente.builder().idCliente("0d25b4ed-f641-4220-8365-7ad4add4ea63cd")
                .nome("Fulano01 da Silva").celular("11985263236").email("fulano01@gmail.com")
                .dtNascimento(new Date()).enderecos(new HashSet<Endereco>(Collections.singletonList(Endereco.builder().endereco("Rua tal 01").bairro("Tal")
                        .cep("11250000").cidade("São Paulo").estado(Estado.SAO_PAULO).build()))).build();

        Set<EnderecoResponse> enderecos = toEnderecoResponse(cliente.getEnderecos());

        serviceResponse.setResult(toClienteResponse(cliente, enderecos));
        return serviceResponse;
    }

    protected ServiceResponse gerarClienteNaoEncontradoResponse(String nomeCliente){
        final ServiceResponse serviceResponse = new ServiceResponse<>();
        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nomeCliente));
        return serviceResponse;
    }
}
