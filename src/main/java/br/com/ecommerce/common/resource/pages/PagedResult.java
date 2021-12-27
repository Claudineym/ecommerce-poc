/*
* Copyright(c) by VR Benefícios
*
* All rights reserved.
*
* This software is confidential and proprietary information of
* VR Benefícios ("Confidential Information").
* You shall not disclose such Confidential Information and shall
* use it only in accordance with the terms of the license agreement
* you entered with VR Benefícios.
*/

package br.com.ecommerce.common.resource.pages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe para armazenar valores do Resultado do listar paginado.
 * 
 * @author Verity
 *
 * @param <T> Object - Objeto referente a qualquer entidade utilizada no metodo listar.
 */
@EqualsAndHashCode
@Data
public class PagedResult<T> {
    public static final Long DEFAULT_OFFSET = NumberUtils.LONG_ZERO;

    private PageMetadata metadata;
    private List<T> elements;

    /**
     * Construtor padrão.
     * 
     * @param elements
     *            List - Objetos referente a entidade de consulta
     * @param totalElements
     *            Long - Quantidade de Resgistros encontrados
     * @param offset
     *            Long - Valor inicial para comecar a partir
     * @param limit
     *            Integer - Limite de registros retornados por resultado
     */
    public PagedResult(List<T> elements, Long totalElements, Long offset, Long limit) {
        this.elements = elements;
        this.metadata = new PageMetadata(totalElements, offset, limit);
    }

    public PagedResult(List<T> content) {
        this(content, Long.valueOf(Optional.of(content).orElse(new ArrayList<>()).size()),
                NumberUtils.LONG_ZERO, Long.valueOf(Optional.of(content).orElse(new ArrayList<>()).size()));
    }

    public Boolean hasMore() {
        return this.metadata.getCount() > this.metadata.getOffset() + this.metadata.getLimit();
    }

    public Boolean hasPrevious() {
        return this.metadata.getOffset() > 0 && this.metadata.getCount() > 0;
    }

}
