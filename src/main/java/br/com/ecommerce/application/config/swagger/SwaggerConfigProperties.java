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
package br.com.ecommerce.application.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigProperties {

  private String termosDeServicoUrl;
  private String groupName;
  private String tituloDocumentacao;
  private String descricaoDocumentacao;
  private String resourcesBasePackage;
  private String apiKeyName;
  private String email;
  private String apiVersion;
  private String apiRepositoryUrl;
}
