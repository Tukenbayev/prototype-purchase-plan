import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlanItem } from 'app/shared/model/plan-item.model';
import { PlanItemService } from './plan-item.service';
import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';
import { PurchasePlanService } from 'app/entities/purchase-plan';

@Component({
    selector: 'jhi-plan-item-update',
    templateUrl: './plan-item-update.component.html'
})
export class PlanItemUpdateComponent implements OnInit {
    planItem: IPlanItem;
    isSaving: boolean;
    purchasePlan: IPurchasePlan;

    purchaseplans: IPurchasePlan[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private planItemService: PlanItemService,
        private purchasePlanService: PurchasePlanService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planItem }) => {
            this.planItem = planItem;
        });
        this.purchasePlanService.query().subscribe(
            (res: HttpResponse<IPurchasePlan[]>) => {
                this.purchaseplans = res.body;
                this.purchasePlan = this.purchaseplans.filter(plan => plan.id === +this.activatedRoute.snapshot.queryParams['pid'])[0];
                console.log(this.purchasePlan);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.planItem.id !== undefined) {
            this.subscribeToSaveResponse(this.planItemService.update(this.planItem));
        } else {
            this.planItem.purchasePlan = this.purchasePlan;
            this.subscribeToSaveResponse(this.planItemService.create(this.planItem));
        }
    }

    calculateTotalPrice() {
        this.planItem.priceWithoutVAT = this.planItem.unitPrice * this.planItem.quantityOrVolume;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanItem>>) {
        result.subscribe((res: HttpResponse<IPlanItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPurchasePlanById(index: number, item: IPurchasePlan) {
        return item.id;
    }
}
