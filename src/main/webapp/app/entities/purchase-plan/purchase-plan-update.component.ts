import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPurchasePlan, PurchasePlanStatus } from 'app/shared/model/purchase-plan.model';
import { PurchasePlanService } from './purchase-plan.service';

@Component({
    selector: 'jhi-purchase-plan-update',
    templateUrl: './purchase-plan-update.component.html'
})
export class PurchasePlanUpdateComponent implements OnInit {
    purchasePlan: IPurchasePlan;
    isSaving: boolean;

    constructor(private purchasePlanService: PurchasePlanService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchasePlan }) => {
            this.purchasePlan = purchasePlan;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchasePlan.id !== undefined) {
            this.subscribeToSaveResponse(this.purchasePlanService.update(this.purchasePlan));
        } else {
            this.purchasePlan.status = PurchasePlanStatus.DRAFT;
            this.subscribeToSaveResponse(this.purchasePlanService.create(this.purchasePlan));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPurchasePlan>>) {
        result.subscribe((res: HttpResponse<IPurchasePlan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
