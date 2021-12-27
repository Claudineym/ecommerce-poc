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

public class SortByInvalidColumnException extends BusinessException {

  @Getter private final String invalidColumn;

  public SortByInvalidColumnException(String invalidColumn) {
    super("-999", "Coluna de ordenação inválida");
    this.invalidColumn = invalidColumn;
  }
}
