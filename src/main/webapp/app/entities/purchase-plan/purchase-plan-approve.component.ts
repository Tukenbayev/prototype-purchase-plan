import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';
import { JhiEventManager } from 'ng-jhipster';
import { PurchasePlanService } from 'app/entities/purchase-plan/purchase-plan.service';

@Component({
    selector: 'jhi-purchase-plan-approve',
    templateUrl: './purchase-plan-approve.component.html'
})
export class PurchasePlanApproveComponent {
    purchasePlan: IPurchasePlan;

    constructor(
        private purchasePlanService: PurchasePlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    approve() {
        const formData: FormData = new FormData();
        const files = document.querySelector('#fileUploadInput').files;
        for (let i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
            console.log(files[i]);
        }

        formData.append('planId', this.purchasePlan.id.toString());
        this.purchasePlanService.approve(formData).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchasePlanListModification',
                content: 'Approved an purchasePlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-plan-approve-popup',
    template: ''
})
export class PurchasePlanApprovePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchasePlan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchasePlanApproveComponent as Component, {
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
