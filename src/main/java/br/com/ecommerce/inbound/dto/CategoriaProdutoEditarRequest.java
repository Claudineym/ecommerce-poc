package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Builder
@Getter
public class CategoriaProdutoEditarRequest {

    @NotNull(message = "Categoria não pode ser nulo")
    @NotEmpty(message = "Categoria não pode ser vazia")
    private String descCategoriaProduto;

    public CategoriaProduto update(CategoriaProduto categoriaProduto){
        if(Objects.nonNull(categoriaProduto.getDescCategoriaProduto())){
            if(!this.descCategoriaProduto.equals(categoriaProduto.getDescCategoriaProduto())){
                categoriaProduto.setDescCategoriaProduto(categoriaProduto.getDescCategoriaProduto());
            }
        }
        return categoriaProduto;
    }
}
