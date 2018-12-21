import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PrototypePurchasePlanModule } from './purchase-plan/purchase-plan.module';
import { PrototypePlanItemModule } from './plan-item/plan-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PrototypePurchasePlanModule,
        PrototypePlanItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PrototypeEntityModule {}
