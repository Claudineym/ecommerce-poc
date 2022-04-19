package br.com.ecommerce.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCriteria {

    @PositiveOrZero(message = "Limit não pode ser negativo")
    @Min(value = 1, message = "Limite de resultados não pode ser igual a Zero ou Negativo")
    @Max(
            value = 100,
            message = "Quantidade máxima de resultados por página não pode ser superior a 100")
    private int limit = 10;

    @PositiveOrZero(message = "Offset não pode ser negativo")
    private int offset = 1;
}
