/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrototypeTestModule } from '../../../test.module';
import { PlanItemDetailComponent } from 'app/entities/plan-item/plan-item-detail.component';
import { PlanItem } from 'app/shared/model/plan-item.model';

describe('Component Tests', () => {
    describe('PlanItem Management Detail Component', () => {
        let comp: PlanItemDetailComponent;
        let fixture: ComponentFixture<PlanItemDetailComponent>;
        const route = ({ data: of({ planItem: new PlanItem(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrototypeTestModule],
                declarations: [PlanItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planItem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
