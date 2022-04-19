package br.com.ecommerce.business.domain.service;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import br.com.ecommerce.business.domain.repository.CategoriaProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaProdutoService {

    private final CategoriaProdutoRepository repository;

    public CategoriaProduto criar(CategoriaProduto categoriaProduto) {
        return repository.save(categoriaProduto);
    }

    public List<CategoriaProduto> listar() {
       return repository.findAll();
    }

    public CategoriaProduto alterar(CategoriaProduto categoriaProduto) {
        return repository.save(categoriaProduto);
    }


    public void excluir(CategoriaProduto categoriaProduto) {
        repository.delete(categoriaProduto);
    }

}
