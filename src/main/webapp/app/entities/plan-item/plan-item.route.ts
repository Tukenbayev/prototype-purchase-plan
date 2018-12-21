import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PlanItem } from 'app/shared/model/plan-item.model';
import { PlanItemService } from './plan-item.service';
import { PlanItemComponent } from './plan-item.component';
import { PlanItemDetailComponent } from './plan-item-detail.component';
import { PlanItemUpdateComponent } from './plan-item-update.component';
import { PlanItemDeletePopupComponent } from './plan-item-delete-dialog.component';
import { IPlanItem } from 'app/shared/model/plan-item.model';

@Injectable({ providedIn: 'root' })
export class PlanItemResolve implements Resolve<IPlanItem> {
    constructor(private service: PlanItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PlanItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PlanItem>) => response.ok),
                map((planItem: HttpResponse<PlanItem>) => planItem.body)
            );
        }
        return of(new PlanItem());
    }
}

export const planItemRoute: Routes = [
    {
        path: 'plan-item',
        component: PlanItemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'prototypeApp.planItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-item/:id/view',
        component: PlanItemDetailComponent,
        resolve: {
            planItem: PlanItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.planItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-item/new',
        component: PlanItemUpdateComponent,
        resolve: {
            planItem: PlanItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.planItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'plan-item/:id/edit',
        component: PlanItemUpdateComponent,
        resolve: {
            planItem: PlanItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.planItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planItemPopupRoute: Routes = [
    {
        path: 'plan-item/:id/delete',
        component: PlanItemDeletePopupComponent,
        resolve: {
            planItem: PlanItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'prototypeApp.planItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
