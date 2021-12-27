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

package br.com.ecommerce.common.validator;

import br.com.ecommerce.common.exception.BusinessException;
import br.com.ecommerce.common.message.Mensagem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Classe de validação dos parametros do Header.
 * 
 * @author verity
 */
@Component
public class HeaderParamValidator {

    private static final String CAMPO_SIGLA_EMISSOR = "siglaEmissor";
    private static final Integer MAX_VALUE_SIGLA_EMISSOR = 5;

    private HeaderParamValidator() {}

    /**
     * Método de validação do siglaEmissor.
     * 
     * @param headers
     *          HttpHeaders - Objeto com o cabeçalho da requisição http contendo o siglaEmissor.
     * 
     * @return String
     *          Caso valide com sucesso, retorna a siglaEmissor do header.
     * 
     * @throws BusinessException
     *          Utilizada para capturar as exceções do microsserviço Contrato.
     * 
     */
    public String validarSiglaEmissor(HttpHeaders headers) throws BusinessException {
        final List<String> siglaEmissorValues = headers.get(CAMPO_SIGLA_EMISSOR);

        HeaderParamValidator.validarSiglaEmissorNulo(siglaEmissorValues);
        HeaderParamValidator.validarSiglaEmissorInvalido(siglaEmissorValues);

        return siglaEmissorValues.get(0);
    }

    /**
     * Método de validação se existe siglaEmissor no header.
     * 
     * @param siglaEmissorValues
     *          List String - recebe a captura do siglaEmissor do header.
     * 
     * @throws BusinessException
     *          Utilizada para capturar as exceções do microsserviço Contrato.
     */
    private static void validarSiglaEmissorNulo(List<String> siglaEmissorValues)
            throws BusinessException {

        if (CollectionUtils.isEmpty(siglaEmissorValues)) {
            throw new BusinessException(Mensagem.OBRIGATORIO.getCodigo(), Mensagem.OBRIGATORIO.showCode(CAMPO_SIGLA_EMISSOR));
        }
    }

    /**
     * Método de validação se a siglaEmissor do header está válida.
     * 
     * @param siglaEmissorValues
     *          List String - recebe a captura do siglaEmissor do header.
     * 
     * @throws BusinessException
     *          Utilizada para capturar as exceções do microsserviço Contrato.
     */
    private static void validarSiglaEmissorInvalido(List<String> siglaEmissorValues)
            throws BusinessException {

        if (siglaEmissorValues.size() > 1
            || StringUtils.isBlank(siglaEmissorValues.get(0)) 
            || siglaEmissorValues.get(0).length() > MAX_VALUE_SIGLA_EMISSOR) {

            throw new BusinessException(Mensagem.INVALIDO.getCodigo(), Mensagem.INVALIDO.showCode(CAMPO_SIGLA_EMISSOR));
        }
    }

}
