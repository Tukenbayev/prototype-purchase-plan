package kz.sapasoft.prototype.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import kz.sapasoft.prototype.domain.enumeration.PlanType;

import kz.sapasoft.prototype.domain.enumeration.PurchasePlanStatus;

/**
 * A PurchasePlan.
 */
@Entity
@Table(name = "purchase_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PurchasePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "fiscal_year", nullable = false)
    private String fiscalYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", nullable = false)
    private PlanType planType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PurchasePlanStatus status;

    @OneToMany(mappedBy = "purchasePlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PlanItem> planItems = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public PurchasePlan fiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
        return this;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public PurchasePlan planType(PlanType planType) {
        this.planType = planType;
        return this;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public PurchasePlanStatus getStatus() {
        return status;
    }

    public PurchasePlan status(PurchasePlanStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PurchasePlanStatus status) {
        this.status = status;
    }

    public Set<PlanItem> getPlanItems() {
        return planItems;
    }

    public PurchasePlan planItems(Set<PlanItem> planItems) {
        this.planItems = planItems;
        return this;
    }

    public PurchasePlan addPlanItems(PlanItem planItem) {
        this.planItems.add(planItem);
        planItem.setPurchasePlan(this);
        return this;
    }

    public PurchasePlan removePlanItems(PlanItem planItem) {
        this.planItems.remove(planItem);
        planItem.setPurchasePlan(null);
        return this;
    }

    public void setPlanItems(Set<PlanItem> planItems) {
        this.planItems = planItems;
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
        PurchasePlan purchasePlan = (PurchasePlan) o;
        if (purchasePlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), purchasePlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PurchasePlan{" +
            "id=" + getId() +
            ", fiscalYear='" + getFiscalYear() + "'" +
            ", planType='" + getPlanType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
