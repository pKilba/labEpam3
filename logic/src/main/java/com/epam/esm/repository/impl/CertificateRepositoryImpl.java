package com.epam.esm.repository.impl;

import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.api.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<Certificate> implements CertificateRepository {

    private static final String TAG_LIST = "tagList";

    @Autowired
    public CertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Certificate.class);
    }

    public Optional<Certificate> findByName(String name) {

        return findByField("name", name);
    }

    public List<Certificate> findAllWithSortingFiltering(
            List<String> tagNames, String partInfo,
            int page, int size) {
        CriteriaQuery<Certificate> query = builder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (tagNames != null) {
            predicates.add(buildPredicateByTagName(root, tagNames));
        }
        if (partInfo != null) {
            predicates.add(buildPredicateByPartInfo(root, partInfo));
        }

        if (!predicates.isEmpty()) {
            query.where(utilBuilderQuery.buildAndPredicates(predicates));
            if (tagNames != null) {
                query.groupBy(root.get("id"));
                query.having(builder.greaterThanOrEqualTo(builder.count(root), (long) tagNames.size()));
            }
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        query.orderBy(criteriaBuilder.asc(root.get("name")));
        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    private Predicate buildPredicateByPartInfo(Root<Certificate> root, String partInfo) {
        String regexValue = utilBuilderQuery.buildRegexValue(partInfo);
        Predicate predicateByNameInfo = builder.like(root.get("name"), regexValue);
        Predicate predicateByDescriptionInfo = builder.like(root.get("description"), regexValue);

        return builder.or(predicateByNameInfo, predicateByDescriptionInfo);
    }

    private Predicate buildPredicateByTagName(Root<Certificate> root, List<String> tagNames) {
        Join<Certificate, Tag> tagJoin = root.join(TAG_LIST);

        return utilBuilderQuery.buildOrEqualPredicates(tagJoin, "name", tagNames);
    }

}
