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

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerDocumentationConfig {

    private static final String REGEX_PACKAGE = "/v[0-9]+/admin/.*";

    private final SwaggerConfigProperties properties;


    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .packagesToScan("br.com.ecommerce.inbound.facade.resource.v1")
                .group("ecommerce-public")
                .pathsToMatch("/v1/public/**")
                .build();
    }
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .packagesToScan("br.com.ecommerce.inbound.facade.resource.v1.admin")
                .group("ecommerce-admin")
                .pathsToMatch("/v1/admin/**")
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(properties.getTituloDocumentacao())
                                .description(properties.getDescricaoDocumentacao())
                                .termsOfService(properties.getTermosDeServicoUrl())
                                .version(properties.getApiVersion())
                                .contact(
                                        new io.swagger.v3.oas.models.info.Contact()
                                                .name(properties.getTituloDocumentacao())
                                                .url(properties.getApiRepositoryUrl())
                                                .email(properties.getEmail())));
    }
}
