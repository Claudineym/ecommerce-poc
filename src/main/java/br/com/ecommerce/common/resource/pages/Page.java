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

/**
 * Classe para armazenar valores da Páginação passados como paramêtros pelo Listar.
 * 
 * @author Verity
 *
 */
@EqualsAndHashCode
@Data
public class Page {

    private Long offset;
    private Long limit;

    public Page(Long offset, Long limit) { 
        this.offset = offset;
        this.limit = limit;
    }
}
