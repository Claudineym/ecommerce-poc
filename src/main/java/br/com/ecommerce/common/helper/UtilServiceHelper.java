package br.com.ecommerce.common.helper;

import br.com.ecommerce.business.domain.entity.Cliente;
import br.com.ecommerce.inbound.dto.SearchCriteria;
import com.google.common.base.Preconditions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toCollection;

@Component
public class UtilServiceHelper<T> {

    private static final String SORT_ORDER_REGEX = "^([a-zA-Z0-9]*)(\\.(asc|desc))?";
    private static final int FIELD_NAME_GROUP_ORDER = 1;
    private static final int SORT_ORDER_DIRECTION_GROUP_ORDER = 3;
    private static final String NOME = "nome";
    private static final String DEFAULT_ORDER_COLUMN = ".asc";

    public Specification<T> getCriteria(
            SearchCriteria searchCriteria) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(buildPredicates(root, criteriaBuilder, searchCriteria));
    }


    public Predicate[] buildPredicates(
            Root<T> root,
            CriteriaBuilder criteriaBuilder,
            SearchCriteria searchCriteria) {
        List<Predicate> predicates = new ArrayList<>();

        return predicates.toArray(new Predicate[0]);
    }

    public PageRequest buildPaging(SearchCriteria criteria, Set<String> sortBy) {
        return PageRequest.of(
                criteria.getOffset(),
                criteria.getLimit(),
                Sort.by(new ArrayList<>(getSortedBy(sortBy))));
    }

    public Set<Sort.Order> getSortedBy(Set<String> fields) {
        return Optional.ofNullable(fields).orElse(Collections.singleton(DEFAULT_ORDER_COLUMN)).stream()
                .distinct()
                .map(this::getSortedBy)
                .collect(toCollection(LinkedHashSet::new));
    }

    public Sort.Order getSortedBy(String field) {
        var pattern = Pattern.compile(SORT_ORDER_REGEX, Pattern.MULTILINE);
        var matcher = pattern.matcher(field);

        Preconditions.checkNotNull(field);
        Preconditions.checkArgument(matcher.matches());

        String fieldName = matcher.group(FIELD_NAME_GROUP_ORDER);
        String sortOrderDirection = matcher.group(SORT_ORDER_DIRECTION_GROUP_ORDER);
        return buildSortOrder(fieldName, sortOrderDirection);
    }

    public Sort.Order buildSortOrder(String property, String direction) {
        return new Sort.Order(
                Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC),
                property,
                Sort.NullHandling.NATIVE);
    }
}
