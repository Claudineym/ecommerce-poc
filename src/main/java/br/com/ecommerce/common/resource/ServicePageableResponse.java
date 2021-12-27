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

package br.com.ecommerce.common.resource;

import br.com.ecommerce.common.resource.pages.PageMetadata;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ServicePageableResponse<T extends Object> extends ServiceResponse<T> {
    
    @Getter
    @Setter
    private PageMetadata metadata;
    
}
