package com.epam.esm.util;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

public class UtilBuilderQuery {
    private static final String ANY_LINE_REGEX = "%";

    private final CriteriaBuilder builder;

    public UtilBuilderQuery(CriteriaBuilder builder) {
        this.builder = builder;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getOptionalQueryResult(Query query) {
        try {

            T entity = (T) query.getSingleResult();
            return Optional.of(entity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public <T> Predicate buildOrEqualPredicates(Path<T> root, String columnName, List<?> values) {
        int counter = 0;
        Predicate predicate = null;
        for (Object value : values) {
            Predicate currentPredicate = builder.equal(root.get(columnName), value);
            if (counter++ == 0) {
                predicate = currentPredicate;
            } else {
                predicate = builder.or(predicate, currentPredicate);
            }
        }

        return predicate;
    }

    public Predicate buildAndPredicates(List<Predicate> predicates) {
        if (predicates == null || predicates.isEmpty()) {
            return null;
        }
        Predicate resultPredicate = predicates.get(0);
        for (int i = 1; i < predicates.size(); ++i) {
            resultPredicate = builder.and(resultPredicate, predicates.get(i));
        }
        return resultPredicate;
    }

    public String buildRegexValue(String value) {
        return String.format("%s%s%s", ANY_LINE_REGEX, value, ANY_LINE_REGEX);
    }

}
