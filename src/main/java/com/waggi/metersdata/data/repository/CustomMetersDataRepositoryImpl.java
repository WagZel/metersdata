package com.waggi.metersdata.data.repository;

import com.waggi.metersdata.data.FilterMetersData;
import com.waggi.metersdata.data.domain.MetersData;
import com.waggi.metersdata.data.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CustomMetersDataRepositoryImpl implements CustomMetersDataRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Object[] findAllByFilter(FilterMetersData filter, User user) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<User, MetersData> metersData = root.join("metersData");

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (!user.getUserRoles().contains(User.UserRole.ROLE_ADMIN)) {

            predicates.add(
                    criteriaBuilder.equal(root.get("id"), user.getId())
            );

        } else if (filter.getUsername() != null) {

            predicates.add(criteriaBuilder.like(
                    root.get("username"), filter.getUsername())
            );
        }

        if (filter.getMeterType() != null) {
            predicates.add(
                    criteriaBuilder.equal(metersData.get("meterType"), filter.getMeterType())
            );
        }

        if (filter.getStartDate() != null) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(metersData.get("dateCreated"), filter.getStartDate())
            );
        }

        predicates.add(
                criteriaBuilder.lessThanOrEqualTo(metersData.get("dateCreated"), filter.getEndDate())
        );

        criteriaQuery.multiselect(root.get("username"), metersData);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));

        Expression sort = filter.getSortField().equals("username") ? root.get("username") : metersData.get(filter.getSortField());

        criteriaQuery.orderBy(filter.getOrder().toLowerCase().equals("asc") ?
                criteriaBuilder.asc(sort) : criteriaBuilder.desc(sort));

        return entityManager.createQuery(criteriaQuery).getResultList().toArray();
    }
}
