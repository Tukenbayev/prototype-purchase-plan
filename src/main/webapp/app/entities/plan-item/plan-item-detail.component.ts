import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanItem } from 'app/shared/model/plan-item.model';

@Component({
    selector: 'jhi-plan-item-detail',
    templateUrl: './plan-item-detail.component.html'
})
export class PlanItemDetailComponent implements OnInit {
    planItem: IPlanItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planItem }) => {
            this.planItem = planItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
