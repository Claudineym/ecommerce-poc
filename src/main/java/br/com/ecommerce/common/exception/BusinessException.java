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

package br.com.ecommerce.common.exception;

import lombok.Getter;

/**
 * Exception de negocios do sistema.
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5812124905468443090L;

    @Getter
    private final String code;

    public BusinessException(final String code, final String message) {
        super(message);
        this.code = code;
        //Como não é passado como parâmetro é apenas inicializado.
    }

    public BusinessException(final String code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
