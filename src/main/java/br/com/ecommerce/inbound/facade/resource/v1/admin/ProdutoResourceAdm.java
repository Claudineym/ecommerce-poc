package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.Produto;
import br.com.ecommerce.business.domain.repository.ProdutoRepository;
import br.com.ecommerce.business.domain.service.ProdutoService;
import br.com.ecommerce.business.domain.service.helper.ProdutoServiceHelper;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServicePageableResponse;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.common.resource.pages.PageMetadata;
import br.com.ecommerce.inbound.dto.*;
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
public class ProdutoResourceAdm implements ProdutoApiAdm{

    private final ProdutoRepository repository;
    private final ProdutoService service;
    private final ProdutoServiceHelper helper;
    private final UtilServiceHelper utilServiceHelper;


    @Override
    public ServiceResponse<ProdutoResponse> criar(ProdutoRequest requisicao) {
        log.debug("Criar produto: {}", requisicao);
        var produto = requisicao.toProduto();

        var retorno = service.criar(produto);

        return ServiceResponse.<ProdutoResponse>builder()
                .result(helper.buildResult(retorno))
                .mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }

    @Override
    public ServiceResponse<Produto> alterar(String idProduto, ProdutoEditarRequest produtoRequest) {
        log.debug("Alterar produto: {}", idProduto);
        final ServiceResponse<Produto> serviceResponse = new ServiceResponse<>();
        Optional<Produto> produtoOptional = repository.findById(idProduto);

        if(!produtoOptional.isPresent()){
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        Produto produto = service.alterar(produtoRequest.updateProduto(produtoOptional.get()));

        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(produto);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> excluir(String idProduto) {
        log.debug("Remoção do produto: {}", idProduto);
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        Optional<Produto> produtoOptional = repository.findById(idProduto);
        if(!produtoOptional.isPresent()){
            serviceResponse.setResult(Boolean.FALSE);
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        service.excluir(produtoOptional.get());
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(Boolean.TRUE);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<ProdutoResponse> consultar(String nomeProduto) {
        log.debug("Consultar produto: {}", nomeProduto);
        final ServiceResponse<ProdutoResponse> serviceResponse = new ServiceResponse<>();

        Optional<Produto> produtoOpt = repository.findByNome(nomeProduto);

        if(produtoOpt.isPresent()){
            Produto produto = produtoOpt.get();

            serviceResponse.setResult(helper.buildResult(produto));
            serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
            serviceResponse.setStatus(HttpStatus.OK);
            log.debug("Resultado da consulta de produtos: {}", serviceResponse.getResult());
            return serviceResponse;
        }

        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(),
                Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", nomeProduto));

        return serviceResponse;
    }

    @Override
    public ServicePageableResponse<List<ProdutoResponse>> listar(SearchCriteria searchCriteria, Set<String> sortBy) {
        ServicePageableResponse<List<ProdutoResponse>> response =
                new ServicePageableResponse<>();

        Specification<Produto> criteria = utilServiceHelper.getCriteria(searchCriteria);

        Page<Produto> pages = service.listar(criteria, searchCriteria, sortBy);
        response.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());

        var pageMetadata =
                new PageMetadata(
                        pages.getTotalElements(),
                        (long) searchCriteria.getOffset(),
                        (long) searchCriteria.getLimit());

        Page<ProdutoResponse> docs = helper.toProdutoResponse(pages);

        response.setMetadata(pageMetadata);
        response.setResult(docs.getContent());

        return response;
    }
}
