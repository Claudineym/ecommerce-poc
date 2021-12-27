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
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Classe para armazenar valores do Metadata.
 * 
 * @author Verity
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@Data
public class PageMetadata {
    public static final Long DEFAULT_OFFSET = NumberUtils.LONG_ZERO;

    private Long count;
    private Long offset;
    private Long limit;

    /**
     * Construtor padrão.
     * 
     * @param totalElements
     *            Long - Quantidade de Resgistros encontrados
     * @param offset
     *            Long - Valor inicial para comecar a partir
     * @param limit
     *            Integer - Limite de registros retornados por resultado
     */
    public PageMetadata(Long totalElements, Long offset, Long limit) {
        this.count = totalElements;
        this.offset = offset;
        this.limit = limit;
    }

    public Boolean hasMore() {
        return count > offset + limit;
    }

    public Boolean hasPrevious() {
        return offset > 0 && count > 0;
    }

}
