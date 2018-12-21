import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';
import { PurchasePlanService } from './purchase-plan.service';

@Component({
    selector: 'jhi-purchase-plan-delete-dialog',
    templateUrl: './purchase-plan-delete-dialog.component.html'
})
export class PurchasePlanDeleteDialogComponent {
    purchasePlan: IPurchasePlan;

    constructor(
        private purchasePlanService: PurchasePlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchasePlanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchasePlanListModification',
                content: 'Deleted an purchasePlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-plan-delete-popup',
    template: ''
})
export class PurchasePlanDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchasePlan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchasePlanDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchasePlan = purchasePlan;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
