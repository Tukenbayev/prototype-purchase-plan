/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { PlanItemUpdateComponent } from 'app/entities/plan-item/plan-item-update.component';
import { PlanItemService } from 'app/entities/plan-item/plan-item.service';
import { PlanItem } from 'app/shared/model/plan-item.model';

describe('Component Tests', () => {
    describe('PlanItem Management Update Component', () => {
        let comp: PlanItemUpdateComponent;
        let fixture: ComponentFixture<PlanItemUpdateComponent>;
        let service: PlanItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrototypeTestModule],
                declarations: [PlanItemUpdateComponent]
            })
                .overrideTemplate(PlanItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanItemService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlanItem(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.planItem = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PlanItem();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.planItem = entity;
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
