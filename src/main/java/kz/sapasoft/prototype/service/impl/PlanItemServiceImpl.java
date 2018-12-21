package kz.sapasoft.prototype.service.impl;

import kz.sapasoft.prototype.domain.FilterCondition;
import kz.sapasoft.prototype.domain.ItemSpecification;
import kz.sapasoft.prototype.service.PlanItemService;
import kz.sapasoft.prototype.domain.PlanItem;
import kz.sapasoft.prototype.repository.PlanItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PlanItem.
 */
@Service
@Transactional
public class PlanItemServiceImpl implements PlanItemService {

    private final Logger log = LoggerFactory.getLogger(PlanItemServiceImpl.class);

    private final PlanItemRepository planItemRepository;

    public PlanItemServiceImpl(PlanItemRepository planItemRepository) {
        this.planItemRepository = planItemRepository;
    }

    /**
     * Save a planItem.
     *
     * @param planItem the entity to save
     * @return the persisted entity
     */
    @Override
    public PlanItem save(PlanItem planItem) {
        log.debug("Request to save PlanItem : {}", planItem);
        return planItemRepository.save(planItem);
    }

    /**
     * Get all the planItems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlanItem> findAll(Pageable pageable) {
        log.debug("Request to get all PlanItems");
        return planItemRepository.findAll(pageable);
    }


    /**
     * Get one planItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlanItem> findOne(Long id) {
        log.debug("Request to get PlanItem : {}", id);
        return planItemRepository.findById(id);
    }

    /**
     * Delete the planItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanItem : {}", id);
        planItemRepository.deleteById(id);
    }

    @Override
    public Page<PlanItem> findByCondition(FilterCondition condition) {
        ItemSpecification specification = new ItemSpecification(condition);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(condition.page, condition.size, sort);
        return planItemRepository.findAll(specification, pageable);
    }

    @Override
    public HttpHeaders getConditionTotalResult(FilterCondition condition) {
        ItemSpecification specification = new ItemSpecification(condition);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(planItemRepository.count(specification)));
        return headers;
    }
}
