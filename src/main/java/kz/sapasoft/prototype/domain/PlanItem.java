package kz.sapasoft.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import kz.sapasoft.prototype.domain.enumeration.PurchasedItemType;

import kz.sapasoft.prototype.domain.enumeration.PurchaseMethod;

import kz.sapasoft.prototype.domain.enumeration.DeliveryMonth;

/**
 * A PlanItem.
 */
@Entity
@Table(name = "plan_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private PurchasedItemType itemType;

    @Column(name = "jhi_row_number")
    private Long rowNumber;

    @NotNull
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_method", nullable = false)
    private PurchaseMethod purchaseMethod;

    @NotNull
    @Column(name = "quantity_or_volume", nullable = false)
    private Float quantityOrVolume;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Float unitPrice;

    @Column(name = "price_without_vat")
    private Float priceWithoutVAT;

    @NotNull
    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_month", nullable = false)
    private DeliveryMonth deliveryMonth;

    @ManyToOne
    @JsonIgnoreProperties("planItems")
    private PurchasePlan purchasePlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchasedItemType getItemType() {
        return itemType;
    }

    public PlanItem itemType(PurchasedItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public void setItemType(PurchasedItemType itemType) {
        this.itemType = itemType;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public PlanItem rowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public PlanItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public PurchaseMethod getPurchaseMethod() {
        return purchaseMethod;
    }

    public PlanItem purchaseMethod(PurchaseMethod purchaseMethod) {
        this.purchaseMethod = purchaseMethod;
        return this;
    }

    public void setPurchaseMethod(PurchaseMethod purchaseMethod) {
        this.purchaseMethod = purchaseMethod;
    }

    public Float getQuantityOrVolume() {
        return quantityOrVolume;
    }

    public PlanItem quantityOrVolume(Float quantityOrVolume) {
        this.quantityOrVolume = quantityOrVolume;
        return this;
    }

    public void setQuantityOrVolume(Float quantityOrVolume) {
        this.quantityOrVolume = quantityOrVolume;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public PlanItem unitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getPriceWithoutVAT() {
        return priceWithoutVAT;
    }

    public PlanItem priceWithoutVAT(Float priceWithoutVAT) {
        this.priceWithoutVAT = priceWithoutVAT;
        return this;
    }

    public void setPriceWithoutVAT(Float priceWithoutVAT) {
        this.priceWithoutVAT = priceWithoutVAT;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public PlanItem deliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryMonth getDeliveryMonth() {
        return deliveryMonth;
    }

    public PlanItem deliveryMonth(DeliveryMonth deliveryMonth) {
        this.deliveryMonth = deliveryMonth;
        return this;
    }

    public void setDeliveryMonth(DeliveryMonth deliveryMonth) {
        this.deliveryMonth = deliveryMonth;
    }

    public PurchasePlan getPurchasePlan() {
        return purchasePlan;
    }

    public PlanItem purchasePlan(PurchasePlan purchasePlan) {
        this.purchasePlan = purchasePlan;
        return this;
    }

    public void setPurchasePlan(PurchasePlan purchasePlan) {
        this.purchasePlan = purchasePlan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlanItem planItem = (PlanItem) o;
        if (planItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanItem{" +
            "id=" + getId() +
            ", itemType='" + getItemType() + "'" +
            ", rowNumber=" + getRowNumber() +
            ", itemName='" + getItemName() + "'" +
            ", purchaseMethod='" + getPurchaseMethod() + "'" +
            ", quantityOrVolume=" + getQuantityOrVolume() +
            ", unitPrice=" + getUnitPrice() +
            ", priceWithoutVAT=" + getPriceWithoutVAT() +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", deliveryMonth='" + getDeliveryMonth() + "'" +
            "}";
    }
}
