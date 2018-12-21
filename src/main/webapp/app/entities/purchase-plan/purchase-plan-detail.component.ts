import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';

@Component({
    selector: 'jhi-purchase-plan-detail',
    templateUrl: './purchase-plan-detail.component.html'
})
export class PurchasePlanDetailComponent implements OnInit {
    purchasePlan: IPurchasePlan;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchasePlan }) => {
            this.purchasePlan = purchasePlan;
        });
    }

    previousState() {
        window.history.back();
    }
}
