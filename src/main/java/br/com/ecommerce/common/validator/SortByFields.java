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

import br.com.ecommerce.common.enums.SortByAllowedFields;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SortByFieldsValidator.class)
@Target({PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface SortByFields {

  Class<SortByAllowedFields> enumClass();

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
