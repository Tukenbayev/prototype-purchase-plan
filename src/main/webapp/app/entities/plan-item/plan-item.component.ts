import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPlanItem } from 'app/shared/model/plan-item.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PlanItemService } from './plan-item.service';
import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';
import { PurchasePlanService } from 'app/entities/purchase-plan';

@Component({
    selector: 'jhi-plan-item',
    templateUrl: './plan-item.component.html'
})
export class PlanItemComponent implements OnInit, OnDestroy {
    currentAccount: any;
    planItems: IPlanItem[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    purchasePlan: IPurchasePlan;
    condition: any;

    constructor(
        private planItemService: PlanItemService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private planService: PurchasePlanService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
        });

        this.planService.find(this.activatedRoute.snapshot.queryParams['pid']).subscribe(res => (this.purchasePlan = res.body));
    }

    loadAll() {
        console.log(this.purchasePlan);
        this.planItemService
            .filter(this.condition)
            .subscribe(
                (res: HttpResponse<IPlanItem[]>) => this.paginatePlanItems(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.loadAll();
    }

    ngOnInit() {
        this.condition = {
            purchasePlanId: +this.activatedRoute.snapshot.queryParams['pid'],
            purchaseMethod: undefined,
            itemType: undefined,
            deliveryMonth: undefined,
            page: this.page - 1,
            size: this.itemsPerPage
        };
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPlanItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPlanItem) {
        return item.id;
    }

    registerChangeInPlanItems() {
        this.eventSubscriber = this.eventManager.subscribe('planItemListModification', response => this.loadAll());
    }

    previousState() {
        window.history.back();
    }

    downloadFile(fileName: string) {
        this.planService.downloadFile(fileName);
    }

    private paginatePlanItems(data: IPlanItem[], headers: HttpHeaders) {
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.planItems = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
