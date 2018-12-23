import { IPlanItem } from 'app/shared/model//plan-item.model';

export const enum PlanType {
    ANNUAL = 'ANNUAL',
    ENUMERATION = 'ENUMERATION'
}

export const enum PurchasePlanStatus {
    DRAFT = 'DRAFT',
    APPROVED = 'APPROVED'
}

export interface IPurchasePlan {
    id?: number;
    fiscalYear?: string;
    planType?: PlanType;
    status?: PurchasePlanStatus;
    planItems?: IPlanItem[];
    approvementFiles?: any;
}

export class PurchasePlan implements IPurchasePlan {
    constructor(
        public id?: number,
        public fiscalYear?: string,
        public planType?: PlanType,
        public status?: PurchasePlanStatus,
        public planItems?: IPlanItem[],
        public approvementFiles?: any
    ) {}
}
