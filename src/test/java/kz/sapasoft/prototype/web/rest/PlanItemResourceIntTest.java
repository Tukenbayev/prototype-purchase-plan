package kz.sapasoft.prototype.web.rest;

import kz.sapasoft.prototype.PrototypeApp;

import kz.sapasoft.prototype.domain.PlanItem;
import kz.sapasoft.prototype.repository.PlanItemRepository;
import kz.sapasoft.prototype.service.PlanItemService;
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

import kz.sapasoft.prototype.domain.enumeration.PurchasedItemType;
import kz.sapasoft.prototype.domain.enumeration.PurchaseMethod;
import kz.sapasoft.prototype.domain.enumeration.DeliveryMonth;
/**
 * Test class for the PlanItemResource REST controller.
 *
 * @see PlanItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrototypeApp.class)
public class PlanItemResourceIntTest {

    private static final PurchasedItemType DEFAULT_ITEM_TYPE = PurchasedItemType.PRODUCT;
    private static final PurchasedItemType UPDATED_ITEM_TYPE = PurchasedItemType.WORK;

    private static final Long DEFAULT_ROW_NUMBER = 1L;
    private static final Long UPDATED_ROW_NUMBER = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final PurchaseMethod DEFAULT_PURCHASE_METHOD = PurchaseMethod.OPEN_TENDER;
    private static final PurchaseMethod UPDATED_PURCHASE_METHOD = PurchaseMethod.OPEN_TENDER_FOR_FALL;

    private static final Float DEFAULT_QUANTITY_OR_VOLUME = 1F;
    private static final Float UPDATED_QUANTITY_OR_VOLUME = 2F;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final Float DEFAULT_PRICE_WITHOUT_VAT = 1F;
    private static final Float UPDATED_PRICE_WITHOUT_VAT = 2F;

    private static final String DEFAULT_DELIVERY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS = "BBBBBBBBBB";

    private static final DeliveryMonth DEFAULT_DELIVERY_MONTH = DeliveryMonth.JANUARY;
    private static final DeliveryMonth UPDATED_DELIVERY_MONTH = DeliveryMonth.FEBRUARY;

    @Autowired
    private PlanItemRepository planItemRepository;

    @Autowired
    private PlanItemService planItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlanItemMockMvc;

    private PlanItem planItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanItemResource planItemResource = new PlanItemResource(planItemService);
        this.restPlanItemMockMvc = MockMvcBuilders.standaloneSetup(planItemResource)
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
    public static PlanItem createEntity(EntityManager em) {
        PlanItem planItem = new PlanItem()
            .itemType(DEFAULT_ITEM_TYPE)
            .rowNumber(DEFAULT_ROW_NUMBER)
            .itemName(DEFAULT_ITEM_NAME)
            .purchaseMethod(DEFAULT_PURCHASE_METHOD)
            .quantityOrVolume(DEFAULT_QUANTITY_OR_VOLUME)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .priceWithoutVAT(DEFAULT_PRICE_WITHOUT_VAT)
            .deliveryAddress(DEFAULT_DELIVERY_ADDRESS)
            .deliveryMonth(DEFAULT_DELIVERY_MONTH);
        return planItem;
    }

    @Before
    public void initTest() {
        planItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanItem() throws Exception {
        int databaseSizeBeforeCreate = planItemRepository.findAll().size();

        // Create the PlanItem
        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isCreated());

        // Validate the PlanItem in the database
        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeCreate + 1);
        PlanItem testPlanItem = planItemList.get(planItemList.size() - 1);
        assertThat(testPlanItem.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testPlanItem.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
        assertThat(testPlanItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testPlanItem.getPurchaseMethod()).isEqualTo(DEFAULT_PURCHASE_METHOD);
        assertThat(testPlanItem.getQuantityOrVolume()).isEqualTo(DEFAULT_QUANTITY_OR_VOLUME);
        assertThat(testPlanItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testPlanItem.getPriceWithoutVAT()).isEqualTo(DEFAULT_PRICE_WITHOUT_VAT);
        assertThat(testPlanItem.getDeliveryAddress()).isEqualTo(DEFAULT_DELIVERY_ADDRESS);
        assertThat(testPlanItem.getDeliveryMonth()).isEqualTo(DEFAULT_DELIVERY_MONTH);
    }

    @Test
    @Transactional
    public void createPlanItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planItemRepository.findAll().size();

        // Create the PlanItem with an existing ID
        planItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        // Validate the PlanItem in the database
        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkItemTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setItemType(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setItemName(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setPurchaseMethod(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityOrVolumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setQuantityOrVolume(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setUnitPrice(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setDeliveryAddress(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = planItemRepository.findAll().size();
        // set the field null
        planItem.setDeliveryMonth(null);

        // Create the PlanItem, which fails.

        restPlanItemMockMvc.perform(post("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanItems() throws Exception {
        // Initialize the database
        planItemRepository.saveAndFlush(planItem);

        // Get all the planItemList
        restPlanItemMockMvc.perform(get("/api/plan-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].purchaseMethod").value(hasItem(DEFAULT_PURCHASE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].quantityOrVolume").value(hasItem(DEFAULT_QUANTITY_OR_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].priceWithoutVAT").value(hasItem(DEFAULT_PRICE_WITHOUT_VAT.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].deliveryMonth").value(hasItem(DEFAULT_DELIVERY_MONTH.toString())));
    }
    
    @Test
    @Transactional
    public void getPlanItem() throws Exception {
        // Initialize the database
        planItemRepository.saveAndFlush(planItem);

        // Get the planItem
        restPlanItemMockMvc.perform(get("/api/plan-items/{id}", planItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(planItem.getId().intValue()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.rowNumber").value(DEFAULT_ROW_NUMBER.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.purchaseMethod").value(DEFAULT_PURCHASE_METHOD.toString()))
            .andExpect(jsonPath("$.quantityOrVolume").value(DEFAULT_QUANTITY_OR_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.priceWithoutVAT").value(DEFAULT_PRICE_WITHOUT_VAT.doubleValue()))
            .andExpect(jsonPath("$.deliveryAddress").value(DEFAULT_DELIVERY_ADDRESS.toString()))
            .andExpect(jsonPath("$.deliveryMonth").value(DEFAULT_DELIVERY_MONTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanItem() throws Exception {
        // Get the planItem
        restPlanItemMockMvc.perform(get("/api/plan-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanItem() throws Exception {
        // Initialize the database
        planItemService.save(planItem);

        int databaseSizeBeforeUpdate = planItemRepository.findAll().size();

        // Update the planItem
        PlanItem updatedPlanItem = planItemRepository.findById(planItem.getId()).get();
        // Disconnect from session so that the updates on updatedPlanItem are not directly saved in db
        em.detach(updatedPlanItem);
        updatedPlanItem
            .itemType(UPDATED_ITEM_TYPE)
            .rowNumber(UPDATED_ROW_NUMBER)
            .itemName(UPDATED_ITEM_NAME)
            .purchaseMethod(UPDATED_PURCHASE_METHOD)
            .quantityOrVolume(UPDATED_QUANTITY_OR_VOLUME)
            .unitPrice(UPDATED_UNIT_PRICE)
            .priceWithoutVAT(UPDATED_PRICE_WITHOUT_VAT)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .deliveryMonth(UPDATED_DELIVERY_MONTH);

        restPlanItemMockMvc.perform(put("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanItem)))
            .andExpect(status().isOk());

        // Validate the PlanItem in the database
        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeUpdate);
        PlanItem testPlanItem = planItemList.get(planItemList.size() - 1);
        assertThat(testPlanItem.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testPlanItem.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
        assertThat(testPlanItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testPlanItem.getPurchaseMethod()).isEqualTo(UPDATED_PURCHASE_METHOD);
        assertThat(testPlanItem.getQuantityOrVolume()).isEqualTo(UPDATED_QUANTITY_OR_VOLUME);
        assertThat(testPlanItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testPlanItem.getPriceWithoutVAT()).isEqualTo(UPDATED_PRICE_WITHOUT_VAT);
        assertThat(testPlanItem.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testPlanItem.getDeliveryMonth()).isEqualTo(UPDATED_DELIVERY_MONTH);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanItem() throws Exception {
        int databaseSizeBeforeUpdate = planItemRepository.findAll().size();

        // Create the PlanItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanItemMockMvc.perform(put("/api/plan-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planItem)))
            .andExpect(status().isBadRequest());

        // Validate the PlanItem in the database
        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanItem() throws Exception {
        // Initialize the database
        planItemService.save(planItem);

        int databaseSizeBeforeDelete = planItemRepository.findAll().size();

        // Get the planItem
        restPlanItemMockMvc.perform(delete("/api/plan-items/{id}", planItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanItem> planItemList = planItemRepository.findAll();
        assertThat(planItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanItem.class);
        PlanItem planItem1 = new PlanItem();
        planItem1.setId(1L);
        PlanItem planItem2 = new PlanItem();
        planItem2.setId(planItem1.getId());
        assertThat(planItem1).isEqualTo(planItem2);
        planItem2.setId(2L);
        assertThat(planItem1).isNotEqualTo(planItem2);
        planItem1.setId(null);
        assertThat(planItem1).isNotEqualTo(planItem2);
    }
}
