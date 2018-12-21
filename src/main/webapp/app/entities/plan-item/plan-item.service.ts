import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanItem } from 'app/shared/model/plan-item.model';

type EntityResponseType = HttpResponse<IPlanItem>;
type EntityArrayResponseType = HttpResponse<IPlanItem[]>;

@Injectable({ providedIn: 'root' })
export class PlanItemService {
    public resourceUrl = SERVER_API_URL + 'api/plan-items';

    constructor(private http: HttpClient) {}

    create(planItem: IPlanItem): Observable<EntityResponseType> {
        return this.http.post<IPlanItem>(this.resourceUrl, planItem, { observe: 'response' });
    }

    update(planItem: IPlanItem): Observable<EntityResponseType> {
        return this.http.put<IPlanItem>(this.resourceUrl, planItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlanItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    filter(condition: any): Observable<EntityArrayResponseType> {
        return this.http.post<IPlanItem[]>(this.resourceUrl + '-filter', condition, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
