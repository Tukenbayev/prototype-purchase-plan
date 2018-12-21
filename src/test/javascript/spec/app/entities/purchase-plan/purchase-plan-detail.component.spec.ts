/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { PurchasePlanDetailComponent } from 'app/entities/purchase-plan/purchase-plan-detail.component';
import { PurchasePlan } from 'app/shared/model/purchase-plan.model';

describe('Component Tests', () => {
    describe('PurchasePlan Management Detail Component', () => {
        let comp: PurchasePlanDetailComponent;
        let fixture: ComponentFixture<PurchasePlanDetailComponent>;
        const route = ({ data: of({ purchasePlan: new PurchasePlan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrototypeTestModule],
                declarations: [PurchasePlanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PurchasePlanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PurchasePlanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.purchasePlan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
