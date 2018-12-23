import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchasePlan } from 'app/shared/model/purchase-plan.model';
import { AuthServerProvider } from 'app/core/auth/auth-jwt.service';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IPurchasePlan>;
type EntityArrayResponseType = HttpResponse<IPurchasePlan[]>;

@Injectable({ providedIn: 'root' })
export class PurchasePlanService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-plans';
    public fileResourceUrl = SERVER_API_URL + 'api/downloadFile';

    constructor(private http: HttpClient, private tokenProvider: AuthServerProvider) {}

    create(purchasePlan: IPurchasePlan): Observable<EntityResponseType> {
        return this.http.post<IPurchasePlan>(this.resourceUrl, purchasePlan, { observe: 'response' });
    }

    update(purchasePlan: IPurchasePlan): Observable<EntityResponseType> {
        return this.http.put<IPurchasePlan>(this.resourceUrl, purchasePlan, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPurchasePlan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPurchasePlan[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    approve(data: any) {
        return this.http.post(`${this.resourceUrl}/approve`, data, { observe: 'response' });
    }

    downloadFile(fileName: string) {
        const token = 'Bearer '.concat(this.tokenProvider.getToken());
        const header = new HttpHeaders({ Authorization: token });
        window.open(`${this.fileResourceUrl}/${fileName}`, '_blank');
    }
}
