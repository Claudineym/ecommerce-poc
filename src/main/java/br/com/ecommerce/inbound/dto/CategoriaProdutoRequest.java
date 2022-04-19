package br.com.ecommerce.inbound.dto;

import br.com.ecommerce.business.domain.entity.CategoriaProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProdutoRequest {

    @NotNull(message = "Categoria não pode ser nulo")
    @NotEmpty(message = "Categoria não pode ser vazia")
    private String descCategoriaProduto;

    public CategoriaProduto toCategoriaProduto(){
        return new CategoriaProduto(this.descCategoriaProduto);
    }
}
