package kz.sapasoft.prototype.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.sapasoft.prototype.domain.PurchasePlan;
import kz.sapasoft.prototype.service.PurchasePlanService;
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
 * REST controller for managing PurchasePlan.
 */
@RestController
@RequestMapping("/api")
public class PurchasePlanResource {

    private final Logger log = LoggerFactory.getLogger(PurchasePlanResource.class);

    private static final String ENTITY_NAME = "purchasePlan";

    private final PurchasePlanService purchasePlanService;

    public PurchasePlanResource(PurchasePlanService purchasePlanService) {
        this.purchasePlanService = purchasePlanService;
    }

    /**
     * POST  /purchase-plans : Create a new purchasePlan.
     *
     * @param purchasePlan the purchasePlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchasePlan, or with status 400 (Bad Request) if the purchasePlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-plans")
    @Timed
    public ResponseEntity<PurchasePlan> createPurchasePlan(@Valid @RequestBody PurchasePlan purchasePlan) throws URISyntaxException {
        log.debug("REST request to save PurchasePlan : {}", purchasePlan);
        if (purchasePlan.getId() != null) {
            throw new BadRequestAlertException("A new purchasePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchasePlan result = purchasePlanService.save(purchasePlan);
        return ResponseEntity.created(new URI("/api/purchase-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-plans : Updates an existing purchasePlan.
     *
     * @param purchasePlan the purchasePlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchasePlan,
     * or with status 400 (Bad Request) if the purchasePlan is not valid,
     * or with status 500 (Internal Server Error) if the purchasePlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-plans")
    @Timed
    public ResponseEntity<PurchasePlan> updatePurchasePlan(@Valid @RequestBody PurchasePlan purchasePlan) throws URISyntaxException {
        log.debug("REST request to update PurchasePlan : {}", purchasePlan);
        if (purchasePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchasePlan result = purchasePlanService.save(purchasePlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchasePlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-plans : get all the purchasePlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchasePlans in body
     */
    @GetMapping("/purchase-plans")
    @Timed
    public ResponseEntity<List<PurchasePlan>> getAllPurchasePlans(Pageable pageable) {
        log.debug("REST request to get a page of PurchasePlans");
        Page<PurchasePlan> page = purchasePlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-plans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /purchase-plans/:id : get the "id" purchasePlan.
     *
     * @param id the id of the purchasePlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchasePlan, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-plans/{id}")
    @Timed
    public ResponseEntity<PurchasePlan> getPurchasePlan(@PathVariable Long id) {
        log.debug("REST request to get PurchasePlan : {}", id);
        Optional<PurchasePlan> purchasePlan = purchasePlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchasePlan);
    }

    /**
     * DELETE  /purchase-plans/:id : delete the "id" purchasePlan.
     *
     * @param id the id of the purchasePlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-plans/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchasePlan(@PathVariable Long id) {
        log.debug("REST request to delete PurchasePlan : {}", id);
        purchasePlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
