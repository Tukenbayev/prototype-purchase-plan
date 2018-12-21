/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { PurchasePlanUpdateComponent } from 'app/entities/purchase-plan/purchase-plan-update.component';
import { PurchasePlanService } from 'app/entities/purchase-plan/purchase-plan.service';
import { PurchasePlan } from 'app/shared/model/purchase-plan.model';

describe('Component Tests', () => {
    describe('PurchasePlan Management Update Component', () => {
        let comp: PurchasePlanUpdateComponent;
        let fixture: ComponentFixture<PurchasePlanUpdateComponent>;
        let service: PurchasePlanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrototypeTestModule],
                declarations: [PurchasePlanUpdateComponent]
            })
                .overrideTemplate(PurchasePlanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchasePlanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchasePlanService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchasePlan(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchasePlan = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchasePlan();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchasePlan = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
