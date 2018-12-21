package kz.sapasoft.prototype.service.impl;

import kz.sapasoft.prototype.service.PurchasePlanService;
import kz.sapasoft.prototype.domain.PurchasePlan;
import kz.sapasoft.prototype.repository.PurchasePlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PurchasePlan.
 */
@Service
@Transactional
public class PurchasePlanServiceImpl implements PurchasePlanService {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanServiceImpl.class);

    private final PurchasePlanRepository purchasePlanRepository;

    public PurchasePlanServiceImpl(PurchasePlanRepository purchasePlanRepository) {
        this.purchasePlanRepository = purchasePlanRepository;
    }

    /**
     * Save a purchasePlan.
     *
     * @param purchasePlan the entity to save
     * @return the persisted entity
     */
    @Override
    public PurchasePlan save(PurchasePlan purchasePlan) {
        log.debug("Request to save PurchasePlan : {}", purchasePlan);
        return purchasePlanRepository.save(purchasePlan);
    }

    /**
     * Get all the purchasePlans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchasePlan> findAll(Pageable pageable) {
        log.debug("Request to get all PurchasePlans");
        return purchasePlanRepository.findAll(pageable);
    }


    /**
     * Get one purchasePlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchasePlan> findOne(Long id) {
        log.debug("Request to get PurchasePlan : {}", id);
        return purchasePlanRepository.findById(id);
    }

    /**
     * Delete the purchasePlan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchasePlan : {}", id);
        purchasePlanRepository.deleteById(id);
    }
}
