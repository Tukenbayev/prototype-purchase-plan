import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared';
import {
    PurchasePlanComponent,
    PurchasePlanDetailComponent,
    PurchasePlanUpdateComponent,
    PurchasePlanDeletePopupComponent,
    PurchasePlanDeleteDialogComponent,
    purchasePlanRoute,
    purchasePlanPopupRoute
} from './';

const ENTITY_STATES = [...purchasePlanRoute, ...purchasePlanPopupRoute];

@NgModule({
    imports: [PrototypeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PurchasePlanComponent,
        PurchasePlanDetailComponent,
        PurchasePlanUpdateComponent,
        PurchasePlanDeleteDialogComponent,
        PurchasePlanDeletePopupComponent
    ],
    entryComponents: [
        PurchasePlanComponent,
        PurchasePlanUpdateComponent,
        PurchasePlanDeleteDialogComponent,
        PurchasePlanDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrototypePurchasePlanModule {}
