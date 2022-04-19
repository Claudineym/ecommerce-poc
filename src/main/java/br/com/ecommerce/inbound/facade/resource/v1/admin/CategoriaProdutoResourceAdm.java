package br.com.ecommerce.inbound.facade.resource.v1.admin;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.repository.CategoriaProdutoRepository;
import br.com.ecommerce.business.domain.service.CategoriaProdutoService;
import br.com.ecommerce.business.domain.service.helper.CategoriaProdutoServiceHelper;
import br.com.ecommerce.common.helper.UtilServiceHelper;
import br.com.ecommerce.common.message.Mensagem;
import br.com.ecommerce.common.resource.ServiceResponse;
import br.com.ecommerce.inbound.dto.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@RestController
public class CategoriaProdutoResourceAdm implements CategoriaProdutoApiAdm{

    private final CategoriaProdutoRepository repository;
    private final CategoriaProdutoService service;
    private final CategoriaProdutoServiceHelper helper;
    private final UtilServiceHelper utilServiceHelper;

    @Override
    public ServiceResponse<CategoriaProdutoResponse> criar(CategoriaProdutoRequest requisicao) {
        log.debug("Criar Categoria de produto: {}", requisicao);
        var produto = requisicao.toCategoriaProduto();

        var retorno = service.criar(produto);

        return ServiceResponse.<CategoriaProdutoResponse>builder()
                .result(helper.buildResult(retorno))
                .mensagens(
                        Collections.singletonList(new ServiceResponse.Mensagem(
                                Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao())))
                .build();
    }

    @Override
    public ServiceResponse<CategoriaProduto> alterar(String idCategoriaProduto,
                                                     CategoriaProdutoEditarRequest categoriaProdutoRequest) {
        log.debug("Alterar categoria de produto: {}", idCategoriaProduto);
        final ServiceResponse<CategoriaProduto> serviceResponse = new ServiceResponse<>();
        Optional<CategoriaProduto> categoriaProdutoOptional = repository.findById(idCategoriaProduto);

        if(!categoriaProdutoOptional.isPresent()){
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        CategoriaProduto categoriaProduto = service.alterar(categoriaProdutoRequest.update(categoriaProdutoOptional.get()));

        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(categoriaProduto);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<Boolean> excluir(String idCategoriaProduto) {
        log.debug("Remoção da categoria do produto: {}", idCategoriaProduto);
        final ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        Optional<CategoriaProduto> categoriaProdutoOptional = repository.findById(idCategoriaProduto);
        if(!categoriaProdutoOptional.isPresent()){
            serviceResponse.setResult(Boolean.FALSE);
            serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(), Mensagem.NAO_ENCONTRADO.getDescricao());
            serviceResponse.setStatus(HttpStatus.NOT_FOUND);
            return serviceResponse;
        }

        service.excluir(categoriaProdutoOptional.get());
        serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
        serviceResponse.setResult(Boolean.TRUE);
        serviceResponse.setStatus(HttpStatus.OK);

        return serviceResponse;
    }

    @Override
    public ServiceResponse<CategoriaProdutoResponse> consultar(String desCategoriaProduto) {
        log.debug("Consultar produto: {}", desCategoriaProduto);
        final ServiceResponse<CategoriaProdutoResponse> serviceResponse = new ServiceResponse<>();

        Optional<CategoriaProduto> categoriaProdutoOpt = repository.findByDescCategoriaProduto(desCategoriaProduto);

        if(categoriaProdutoOpt.isPresent()){
            CategoriaProduto categoriaProduto = categoriaProdutoOpt.get();

            serviceResponse.setResult(helper.buildResult(categoriaProduto));
            serviceResponse.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());
            serviceResponse.setStatus(HttpStatus.OK);
            log.debug("Resultado da consulta de produtos: {}", serviceResponse.getResult());
            return serviceResponse;
        }

        serviceResponse.addMensagem(Mensagem.NAO_ENCONTRADO.getCodigo(),
                Mensagem.NAO_ENCONTRADO.getDescricao().replace("${0}", desCategoriaProduto));

        return serviceResponse;
    }

    @Override
    public ServiceResponse<List<CategoriaProdutoResponse>> listar() {
        ServiceResponse<List<CategoriaProdutoResponse>> response = new ServiceResponse<>();
        List<CategoriaProduto> categorias = service.listar();
        response.addMensagem(Mensagem.SUCESSO.getCodigo(), Mensagem.SUCESSO.getDescricao());

        List<CategoriaProdutoResponse> docs = helper.toCategoriaProdutoResponses(categorias);
        response.setResult(docs);
        return response;
    }
}
