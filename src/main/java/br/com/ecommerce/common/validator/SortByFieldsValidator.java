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

import br.com.ecommerce.common.exception.SortByInvalidColumnException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

@Slf4j
public class SortByFieldsValidator implements ConstraintValidator<SortByFields, Set<String>> {

  public static final String SORT_BY_COLUMN_NAME_REGEX = "^([a-zA-Z0-9]*)(\\.(asc|desc))?";
  List<String> allowed;

  @Override
  public void initialize(SortByFields constraintAnnotation) {
    this.allowed =
        Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
            .map(field -> UPPER_UNDERSCORE.to(LOWER_CAMEL, field.name()))
            .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(Set<String> values, ConstraintValidatorContext context) {

    if (ObjectUtils.isNotEmpty(values)) {
      try {
        values.stream()
            .filter(field -> !allowed.contains(field.replaceAll(SORT_BY_COLUMN_NAME_REGEX, "$1")))
            .findAny()
            .ifPresent(
                invalidColumn -> {
                  throw new SortByInvalidColumnException(invalidColumn);
                });
      } catch (SortByInvalidColumnException ex) {
        String err =
            MessageFormat.format("Campo de ordenação inválido: {0}", ex.getInvalidColumn());
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(err).addConstraintViolation();
        return false;
      }
    }
    return true;
  }
}
