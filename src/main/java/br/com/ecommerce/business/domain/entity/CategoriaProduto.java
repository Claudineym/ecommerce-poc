package br.com.ecommerce.business.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoriaProduto {
    @Id
    private String idCategoriaProduto;
    private String descCategoriaProduto;

    public CategoriaProduto(String descCategoriaProduto){
        this.idCategoriaProduto = UUID.randomUUID().toString();
        this.descCategoriaProduto = descCategoriaProduto;
    }
}
