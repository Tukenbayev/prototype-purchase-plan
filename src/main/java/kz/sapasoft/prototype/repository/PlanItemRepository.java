package kz.sapasoft.prototype.repository;

import kz.sapasoft.prototype.domain.PlanItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlanItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanItemRepository extends JpaRepository<PlanItem, Long>, JpaSpecificationExecutor<PlanItem> {

}
