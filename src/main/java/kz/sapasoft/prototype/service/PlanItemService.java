package kz.sapasoft.prototype.service;

import kz.sapasoft.prototype.domain.FilterCondition;
import kz.sapasoft.prototype.domain.PlanItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import javax.swing.text.html.parser.Element;
import java.util.Optional;

/**
 * Service Interface for managing PlanItem.
 */
public interface PlanItemService {

    /**
     * Save a planItem.
     *
     * @param planItem the entity to save
     * @return the persisted entity
     */
    PlanItem save(PlanItem planItem);

    /**
     * Get all the planItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PlanItem> findAll(Pageable pageable);


    /**
     * Get the "id" planItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PlanItem> findOne(Long id);

    /**
     * Delete the "id" planItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    HttpHeaders getConditionTotalResult(FilterCondition condition);

    Page<PlanItem> findByCondition(FilterCondition condition);
}
