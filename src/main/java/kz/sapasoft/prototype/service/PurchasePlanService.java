package kz.sapasoft.prototype.service;

import kz.sapasoft.prototype.domain.ApprovementDTO;
import kz.sapasoft.prototype.domain.PurchasePlan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PurchasePlan.
 */
public interface PurchasePlanService {

    /**
     * Save a purchasePlan.
     *
     * @param purchasePlan the entity to save
     * @return the persisted entity
     */
    PurchasePlan save(PurchasePlan purchasePlan);

    /**
     * Get all the purchasePlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchasePlan> findAll(Pageable pageable);


    /**
     * Get the "id" purchasePlan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchasePlan> findOne(Long id);

    /**
     * Delete the "id" purchasePlan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void approvePlan(ApprovementDTO approvementDTO);
}
