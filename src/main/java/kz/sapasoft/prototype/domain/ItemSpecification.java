package kz.sapasoft.prototype.domain;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class ItemSpecification implements Specification<PlanItem> {

    private FilterCondition condition;

    public ItemSpecification(FilterCondition condition) {
        this.condition = condition;
    }

    @Override
    public Predicate toPredicate(Root<PlanItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (condition.purchaseMethod != null)
            predicates.add(criteriaBuilder.equal(root.get("purchaseMethod"), condition.purchaseMethod));
        if (condition.itemType != null)
            predicates.add(criteriaBuilder.equal(root.get("itemType"), condition.itemType));
        if (condition.deliveryMonth != null)
            predicates.add(criteriaBuilder.equal(root.get("deliveryMonth"), condition.deliveryMonth));

        if (condition.purchasePlanId != null)
            predicates.add(criteriaBuilder.equal(root.get("purchasePlan").get("id"), condition.purchasePlanId));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
