import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PrototypeSharedModule } from 'app/shared';
import {
    PlanItemComponent,
    PlanItemDetailComponent,
    PlanItemUpdateComponent,
    PlanItemDeletePopupComponent,
    PlanItemDeleteDialogComponent,
    planItemRoute,
    planItemPopupRoute
} from './';

const ENTITY_STATES = [...planItemRoute, ...planItemPopupRoute];

@NgModule({
    imports: [PrototypeSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanItemComponent,
        PlanItemDetailComponent,
        PlanItemUpdateComponent,
        PlanItemDeleteDialogComponent,
        PlanItemDeletePopupComponent
    ],
    entryComponents: [PlanItemComponent, PlanItemUpdateComponent, PlanItemDeleteDialogComponent, PlanItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrototypePlanItemModule {}
