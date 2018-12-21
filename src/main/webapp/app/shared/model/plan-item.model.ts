import { IPurchasePlan } from 'app/shared/model//purchase-plan.model';

export const enum PurchasedItemType {
    PRODUCT = 'PRODUCT',
    WORK = 'WORK',
    SERVICE = 'SERVICE'
}

export const enum PurchaseMethod {
    OPEN_TENDER = 'OPEN_TENDER',
    OPEN_TENDER_FOR_FALL = 'OPEN_TENDER_FOR_FALL',
    REQUEST_FOR_PRICE_OFFERS = 'REQUEST_FOR_PRICE_OFFERS',
    REQUEST_FOR_PRICE_OFFERS_FOR_FALL = 'REQUEST_FOR_PRICE_OFFERS_FOR_FALL',
    FROM_ONE_SOURCE = 'FROM_ONE_SOURCE'
}

export const enum DeliveryMonth {
    JANUARY = 'JANUARY',
    FEBRUARY = 'FEBRUARY',
    MARCH = 'MARCH',
    APRIL = 'APRIL',
    MAY = 'MAY',
    JUNE = 'JUNE',
    JULY = 'JULY',
    AUGUST = 'AUGUST',
    SEPTEMBER = 'SEPTEMBER',
    OCTOBER = 'OCTOBER',
    NOVEMBER = 'NOVEMBER',
    DECEMBER = 'DECEMBER'
}

export interface IPlanItem {
    id?: number;
    itemType?: PurchasedItemType;
    rowNumber?: number;
    itemName?: string;
    purchaseMethod?: PurchaseMethod;
    quantityOrVolume?: number;
    unitPrice?: number;
    priceWithoutVAT?: number;
    deliveryAddress?: string;
    deliveryMonth?: DeliveryMonth;
    purchasePlan?: IPurchasePlan;
}

export class PlanItem implements IPlanItem {
    constructor(
        public id?: number,
        public itemType?: PurchasedItemType,
        public rowNumber?: number,
        public itemName?: string,
        public purchaseMethod?: PurchaseMethod,
        public quantityOrVolume?: number,
        public unitPrice?: number,
        public priceWithoutVAT?: number,
        public deliveryAddress?: string,
        public deliveryMonth?: DeliveryMonth,
        public purchasePlan?: IPurchasePlan
    ) {}
}
