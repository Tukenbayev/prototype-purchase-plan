package kz.sapasoft.prototype.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.sapasoft.prototype.domain.FilterCondition;
import kz.sapasoft.prototype.domain.PlanItem;
import kz.sapasoft.prototype.service.PlanItemService;
import kz.sapasoft.prototype.web.rest.errors.BadRequestAlertException;
import kz.sapasoft.prototype.web.rest.util.HeaderUtil;
import kz.sapasoft.prototype.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PlanItem.
 */
@RestController
@RequestMapping("/api")
public class PlanItemResource {

    private final Logger log = LoggerFactory.getLogger(PlanItemResource.class);

    private static final String ENTITY_NAME = "planItem";

    private final PlanItemService planItemService;

    public PlanItemResource(PlanItemService planItemService) {
        this.planItemService = planItemService;
    }

    /**
     * POST  /plan-items : Create a new planItem.
     *
     * @param planItem the planItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new planItem, or with status 400 (Bad Request) if the planItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/plan-items")
    @Timed
    public ResponseEntity<PlanItem> createPlanItem(@Valid @RequestBody PlanItem planItem) throws URISyntaxException {
        log.debug("REST request to save PlanItem : {}", planItem);
        if (planItem.getId() != null) {
            throw new BadRequestAlertException("A new planItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanItem result = planItemService.save(planItem);
        return ResponseEntity.created(new URI("/api/plan-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/plan-items-filter")
    @Timed
    public ResponseEntity<List<PlanItem>> getFilteredPlanItems(@RequestBody FilterCondition condition) throws URISyntaxException {
        HttpHeaders headers = planItemService.getConditionTotalResult(condition);
        return new ResponseEntity<>(planItemService.findByCondition(condition).getContent(), headers, HttpStatus.OK);
    }

    /**
     * PUT  /plan-items : Updates an existing planItem.
     *
     * @param planItem the planItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated planItem,
     * or with status 400 (Bad Request) if the planItem is not valid,
     * or with status 500 (Internal Server Error) if the planItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/plan-items")
    @Timed
    public ResponseEntity<PlanItem> updatePlanItem(@Valid @RequestBody PlanItem planItem) throws URISyntaxException {
        log.debug("REST request to update PlanItem : {}", planItem);
        if (planItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlanItem result = planItemService.save(planItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, planItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /plan-items : get all the planItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of planItems in body
     */
    @GetMapping("/plan-items")
    @Timed
    public ResponseEntity<List<PlanItem>> getAllPlanItems(Pageable pageable) {
        log.debug("REST request to get a page of PlanItems");
        Page<PlanItem> page = planItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plan-items");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /plan-items/:id : get the "id" planItem.
     *
     * @param id the id of the planItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the planItem, or with status 404 (Not Found)
     */
    @GetMapping("/plan-items/{id}")
    @Timed
    public ResponseEntity<PlanItem> getPlanItem(@PathVariable Long id) {
        log.debug("REST request to get PlanItem : {}", id);
        Optional<PlanItem> planItem = planItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planItem);
    }

    /**
     * DELETE  /plan-items/:id : delete the "id" planItem.
     *
     * @param id the id of the planItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/plan-items/{id}")
    @Timed
    public ResponseEntity<Void> deletePlanItem(@PathVariable Long id) {
        log.debug("REST request to delete PlanItem : {}", id);
        planItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
