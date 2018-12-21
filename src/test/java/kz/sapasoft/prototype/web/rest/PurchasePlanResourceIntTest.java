package kz.sapasoft.prototype.web.rest;

import kz.sapasoft.prototype.PrototypeApp;

import kz.sapasoft.prototype.domain.PurchasePlan;
import kz.sapasoft.prototype.repository.PurchasePlanRepository;
import kz.sapasoft.prototype.service.PurchasePlanService;
import kz.sapasoft.prototype.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static kz.sapasoft.prototype.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kz.sapasoft.prototype.domain.enumeration.PlanType;
import kz.sapasoft.prototype.domain.enumeration.PurchasePlanStatus;
/**
 * Test class for the PurchasePlanResource REST controller.
 *
 * @see PurchasePlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class PurchasePlanResourceIntTest {

    private static final String DEFAULT_FISCAL_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_FISCAL_YEAR = "BBBBBBBBBB";

    private static final PlanType DEFAULT_PLAN_TYPE = PlanType.ANNUAL;
    private static final PlanType UPDATED_PLAN_TYPE = PlanType.ENUMERATION;

    private static final PurchasePlanStatus DEFAULT_STATUS = PurchasePlanStatus.DRAFT;
    private static final PurchasePlanStatus UPDATED_STATUS = PurchasePlanStatus.APPROVED;

    @Autowired
    private PurchasePlanRepository purchasePlanRepository;

    @Autowired
    private PurchasePlanService purchasePlanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchasePlanMockMvc;

    private PurchasePlan purchasePlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchasePlanResource purchasePlanResource = new PurchasePlanResource(purchasePlanService);
        this.restPurchasePlanMockMvc = MockMvcBuilders.standaloneSetup(purchasePlanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasePlan createEntity(EntityManager em) {
        PurchasePlan purchasePlan = new PurchasePlan()
            .fiscalYear(DEFAULT_FISCAL_YEAR)
            .planType(DEFAULT_PLAN_TYPE)
            .status(DEFAULT_STATUS);
        return purchasePlan;
    }

    @Before
    public void initTest() {
        purchasePlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasePlan() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanRepository.findAll().size();

        // Create the PurchasePlan
        restPurchasePlanMockMvc.perform(post("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlan)))
            .andExpect(status().isCreated());

        // Validate the PurchasePlan in the database
        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasePlan testPurchasePlan = purchasePlanList.get(purchasePlanList.size() - 1);
        assertThat(testPurchasePlan.getFiscalYear()).isEqualTo(DEFAULT_FISCAL_YEAR);
        assertThat(testPurchasePlan.getPlanType()).isEqualTo(DEFAULT_PLAN_TYPE);
        assertThat(testPurchasePlan.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPurchasePlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasePlanRepository.findAll().size();

        // Create the PurchasePlan with an existing ID
        purchasePlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasePlanMockMvc.perform(post("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlan)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlan in the database
        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFiscalYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasePlanRepository.findAll().size();
        // set the field null
        purchasePlan.setFiscalYear(null);

        // Create the PurchasePlan, which fails.

        restPurchasePlanMockMvc.perform(post("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlan)))
            .andExpect(status().isBadRequest());

        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasePlanRepository.findAll().size();
        // set the field null
        purchasePlan.setPlanType(null);

        // Create the PurchasePlan, which fails.

        restPurchasePlanMockMvc.perform(post("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlan)))
            .andExpect(status().isBadRequest());

        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchasePlans() throws Exception {
        // Initialize the database
        purchasePlanRepository.saveAndFlush(purchasePlan);

        // Get all the purchasePlanList
        restPurchasePlanMockMvc.perform(get("/api/purchase-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].fiscalYear").value(hasItem(DEFAULT_FISCAL_YEAR.toString())))
            .andExpect(jsonPath("$.[*].planType").value(hasItem(DEFAULT_PLAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchasePlan() throws Exception {
        // Initialize the database
        purchasePlanRepository.saveAndFlush(purchasePlan);

        // Get the purchasePlan
        restPurchasePlanMockMvc.perform(get("/api/purchase-plans/{id}", purchasePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchasePlan.getId().intValue()))
            .andExpect(jsonPath("$.fiscalYear").value(DEFAULT_FISCAL_YEAR.toString()))
            .andExpect(jsonPath("$.planType").value(DEFAULT_PLAN_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchasePlan() throws Exception {
        // Get the purchasePlan
        restPurchasePlanMockMvc.perform(get("/api/purchase-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasePlan() throws Exception {
        // Initialize the database
        purchasePlanService.save(purchasePlan);

        int databaseSizeBeforeUpdate = purchasePlanRepository.findAll().size();

        // Update the purchasePlan
        PurchasePlan updatedPurchasePlan = purchasePlanRepository.findById(purchasePlan.getId()).get();
        // Disconnect from session so that the updates on updatedPurchasePlan are not directly saved in db
        em.detach(updatedPurchasePlan);
        updatedPurchasePlan
            .fiscalYear(UPDATED_FISCAL_YEAR)
            .planType(UPDATED_PLAN_TYPE)
            .status(UPDATED_STATUS);

        restPurchasePlanMockMvc.perform(put("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchasePlan)))
            .andExpect(status().isOk());

        // Validate the PurchasePlan in the database
        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeUpdate);
        PurchasePlan testPurchasePlan = purchasePlanList.get(purchasePlanList.size() - 1);
        assertThat(testPurchasePlan.getFiscalYear()).isEqualTo(UPDATED_FISCAL_YEAR);
        assertThat(testPurchasePlan.getPlanType()).isEqualTo(UPDATED_PLAN_TYPE);
        assertThat(testPurchasePlan.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasePlan() throws Exception {
        int databaseSizeBeforeUpdate = purchasePlanRepository.findAll().size();

        // Create the PurchasePlan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchasePlanMockMvc.perform(put("/api/purchase-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasePlan)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasePlan in the database
        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchasePlan() throws Exception {
        // Initialize the database
        purchasePlanService.save(purchasePlan);

        int databaseSizeBeforeDelete = purchasePlanRepository.findAll().size();

        // Get the purchasePlan
        restPurchasePlanMockMvc.perform(delete("/api/purchase-plans/{id}", purchasePlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchasePlan> purchasePlanList = purchasePlanRepository.findAll();
        assertThat(purchasePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasePlan.class);
        PurchasePlan purchasePlan1 = new PurchasePlan();
        purchasePlan1.setId(1L);
        PurchasePlan purchasePlan2 = new PurchasePlan();
        purchasePlan2.setId(purchasePlan1.getId());
        assertThat(purchasePlan1).isEqualTo(purchasePlan2);
        purchasePlan2.setId(2L);
        assertThat(purchasePlan1).isNotEqualTo(purchasePlan2);
        purchasePlan1.setId(null);
        assertThat(purchasePlan1).isNotEqualTo(purchasePlan2);
    }
}
