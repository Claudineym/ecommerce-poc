package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.business.domain.entity.Vendedor;
import br.com.ecommerce.business.domain.repository.VendedorRepository;
import br.com.ecommerce.business.domain.service.VendedorService;
import br.com.ecommerce.business.domain.service.helper.VendedorServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.*;
import br.com.ecommerce.outbound.dto.PessoaResultResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Data
@RestController
public class VendedorResourceAdm implements VendedorApiAdm {

    public static final String SORT_BY = "sort_by";
    public static final String INVALID_SORT_FIELD = "Campo para ordenação inválido";

    private final VendedorRepository repository;
    private final VendedorService vendedorService;
    private final VendedorServiceHelper helper;

    @Override
    public ServiceResponse<PessoaResponse> criar(PessoaRequest requisicao) {
        log.debug("Criar vendedor: {}", requisicao);
        var vendedor = requisicao.toVendedor(helper);

        var clienteRetorno = vendedorService.criar(vendedor);

        Set<EnderecoResponse> enderecosResp = helper.toEnderecoResponse(clienteRetorno.getEnderecos());

        return ServiceResponse.<PessoaResponse>builder()
                .result(helper.toVendedorResponse(clienteRetorno, enderecosResp)
                ).mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }

    @Override
    public ServiceResponse<Vendedor> alterar(String id, PessoaEditarRequest vendedorEditarRequest) {
        log.debug("Alterar vendedor: {}", id);
        final ServiceResponse<Vendedor> serviceResponse = new ServiceResponse<>();
        Optional<Vendedor> vendedorOptional = repository.findById(id);

        if(!vendedorOptional.isPresent()){
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        Vendedor vendedor = vendedorService.alterar(vendedorEditarRequest.updateVendedor(vendedorOptional.get()));

        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(vendedor);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> excluir(String id) {
        log.debug("Remoção do vendedor: {}", id);
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        Optional<Vendedor> vendedorOptional = repository.findById(id);
        if(!vendedorOptional.isPresent()){

            serviceResponse.setResult(Boolean.FALSE);
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);

            return serviceResponse;
        }

        vendedorService.excluir(vendedorOptional.get());
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(Boolean.TRUE);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;

    }

    @Override
    public ServiceResponse<PessoaResponse> consultar(String nomeCliente) {
        log.debug("Consultar vendedor: {}", nomeCliente);
        final ServiceResponse<PessoaResponse> serviceResponse = new ServiceResponse<>();

        Optional<Vendedor> vendedorOptional = repository.findByNome(nomeCliente);

        if(vendedorOptional.isPresent()){
            Vendedor vendedor = vendedorOptional.get();

            Set<EnderecoResponse> enderecos = helper.toEnderecoResponse(vendedor.getEnderecos());

            serviceResponse.setResult(helper.toVendedorResponse(vendedor, enderecos));
            serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
            serviceResponse.setStatus(HttpStatus.OK);
            log.debug("Resultado da consulta de vendedores: {}", serviceResponse.getResult());
            return serviceResponse;
        }

        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nomeCliente));

        return serviceResponse;
    }

    @Override
    public ServicePageableResponse<List<PessoaResultResponse>> listar(PessoaSearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<PessoaResultResponse>> response =
                new ServicePageableResponse<>();

        Specification<Vendedor> criteria = helper.getCriteria(searchCriteria);

        Page<Vendedor> pages = vendedorService.listar(criteria, searchCriteria, sortBy);
        response.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());

        var pageMetadata =
                new PageMetadata(
                        pages.getTotalElements(),
                        (long) searchCriteria.getOffset(),
                        (long) searchCriteria.getLimit());

        Page<PessoaResultResponse> docs = helper.toVendedorResponse(pages);

        response.setMetadata(pageMetadata);
        response.setResult(docs.getContent());

        return response;
    }
}
