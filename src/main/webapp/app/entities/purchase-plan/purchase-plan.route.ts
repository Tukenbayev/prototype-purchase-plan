import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchasePlan } from 'app/shared/model/purchase-plan.model';
import { PurchasePlanService } from './purchase-plan.service';
import { PurchasePlanComponent } from './purchase-plan.component';
import { PurchasePlanDetailComponent } from './purchase-plan-detail.component';
import { PurchasePlanUpdateComponent } from './purchase-plan-update.component';
import { PurchasePlanDeletePopupComponent } from './purchase-plan-delete-dialog.component';
import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';

@Injectable({ providedIn: 'root' })
export class PurchasePlanResolve implements Resolve<IPurchasePlan> {
    constructor(private service: PurchasePlanService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PurchasePlan> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchasePlan>) => response.ok),
                map((purchasePlan: HttpResponse<PurchasePlan>) => purchasePlan.body)
            );
        }
        return of(new PurchasePlan());
    }
}

export const purchasePlanRoute: Routes = [
    {
        path: 'purchase-plan',
        component: PurchasePlanComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'prototypeApp.purchasePlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-plan/:id/view',
        component: PurchasePlanDetailComponent,
        resolve: {
            purchasePlan: PurchasePlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.purchasePlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-plan/new',
        component: PurchasePlanUpdateComponent,
        resolve: {
            purchasePlan: PurchasePlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.purchasePlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'purchase-plan/:id/edit',
        component: PurchasePlanUpdateComponent,
        resolve: {
            purchasePlan: PurchasePlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.purchasePlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchasePlanPopupRoute: Routes = [
    {
        path: 'purchase-plan/:id/delete',
        component: PurchasePlanDeletePopupComponent,
        resolve: {
            purchasePlan: PurchasePlanResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.purchasePlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
