package kz.sapasoft.prototype.repository;

import kz.sapasoft.prototype.domain.PurchasePlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchasePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchasePlanRepository extends JpaRepository<PurchasePlan, Long> {

}
