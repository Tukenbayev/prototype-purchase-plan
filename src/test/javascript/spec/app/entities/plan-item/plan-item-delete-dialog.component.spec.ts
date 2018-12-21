/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PrototypeTestModule } from '../../../test.module';
import { PlanItemDeleteDialogComponent } from 'app/entities/plan-item/plan-item-delete-dialog.component';
import { PlanItemService } from 'app/entities/plan-item/plan-item.service';

describe('Component Tests', () => {
    describe('PlanItem Management Delete Component', () => {
        let comp: PlanItemDeleteDialogComponent;
        let fixture: ComponentFixture<PlanItemDeleteDialogComponent>;
        let service: PlanItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PrototypeTestModule],
                declarations: [PlanItemDeleteDialogComponent]
            })
                .overrideTemplate(PlanItemDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
