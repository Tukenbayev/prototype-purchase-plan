package kz.sapasoft.prototype.domain;

import kz.sapasoft.prototype.domain.enumeration.DeliveryMonth;
import kz.sapasoft.prototype.domain.enumeration.PurchaseMethod;
import kz.sapasoft.prototype.domain.enumeration.PurchasedItemType;

public class FilterCondition {

    public int page;
    public int size;

    public PurchaseMethod purchaseMethod;
    public PurchasedItemType itemType;
    public DeliveryMonth deliveryMonth;
    public Long purchasePlanId;
}
