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
package br.com.ecommerce.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortByAllowedFields {
  IDCLIENTE("idCliente"),
  NOME("nome");

  private final String value;
}
