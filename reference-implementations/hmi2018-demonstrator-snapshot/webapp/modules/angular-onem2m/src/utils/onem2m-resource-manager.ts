import { OneM2MClient } from '../clients/onem2m-client';
import { Observable } from 'rxjs/Observable';
import { Ae, Cb, Cnt, Cin, Rqp, Rsp, PrimitiveContent, Resource } from '../resources/onem2m-resources';
import 'rxjs/add/operator/map'
import { ReplaySubject } from 'rxjs/ReplaySubject';


import { DataResult, ResourceResult } from './onem2m-resource-common-datatypes'
import { OneM2MResourcesUtil } from '../resources/onem2m-resources-util'

export class OneM2MResourceManager {
    private client: OneM2MClient;

    constructor(client: OneM2MClient) {
        this.client = client;
    }

    public getClient(): OneM2MClient {
        return this.client;
    }

    // resources
    public createResource(path: string, resource: Resource, hierarchical?: boolean): Observable<ResourceResult<Resource>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 1;
        if (resource.ty === undefined || resource.ty === null) {
            resource.ty = OneM2MResourcesUtil.getTyFromResource(resource);
            if (resource.ty === undefined || resource.ty === null) {
                throw new Error("Resource type was not set. Automatic detection failed.");
            }
        }
        rqp.ty = resource.ty;
        rqp.pc = new PrimitiveContent;
        var payload = "{\"" + OneM2MResourcesUtil.getXsdTypeNameFromResource(resource) + "\":" + JSON.stringify(resource) + "}";
        rqp.pc.anyOrAny.push(payload);
        return this.client.send(rqp).map(response => new ResourceResult<Resource>(response, (response.rsc === 2001) ? response.pc.anyOrAny[0] : null));
    }


    public retrieveResource(path: string, hierarchical?: boolean): Observable<ResourceResult<Resource>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 2;
        rqp.pc = new PrimitiveContent;
        return this.client.send(rqp).map(response => new ResourceResult<Resource>(response, (response.rsc === 2000) ? response.pc.anyOrAny[0] : null));
    }

    public retrieveResourceWithChildren(path: string, hierarchical?: boolean): Observable<ResourceResult<Resource>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 2;
        rqp.rcn = 4; // add children
        rqp.pc = new PrimitiveContent;
        return this.client.send(rqp).map(response => new ResourceResult<Resource>(response, (response.rsc === 2000) ? response.pc.anyOrAny[0] : null),
                        error => new ResourceResult<Resource>(error, null));
    }

    public deleteResource(path: string, hierarchical?: boolean): Observable<DataResult<Boolean>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 4;
        return this.client.send(rqp).map(response => new DataResult<Boolean>(response, (response.rsc === 2002)));
    }

    // Cb
    public retrieveCSEBase(): Observable<ResourceResult<Cb>> {
        return this.retrieveResource('', false).map(result => (result.resource instanceof Cb ? new ResourceResult<Cb>(result.response, result.resource) : new ResourceResult<Cb>(result.response, null)));
    }

    // ae
    public createApplicationEntity(path: string, ae: Ae, hierarchical?: boolean): Observable<ResourceResult<Ae>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 1;
        rqp.ty = 2;
        rqp.pc = new PrimitiveContent;
        var payload = "{\"m2m:ae\":" + JSON.stringify(ae) + "}";
        rqp.pc.anyOrAny.push(payload);
        return this.client.send(rqp).map(response => new ResourceResult<Ae>(response, (response.rsc === 2001) ? response.pc.anyOrAny[0] : null));
    }

    // container
    public createContainer(path: string, cnt: Cnt, hierarchical?: boolean): Observable<ResourceResult<Cnt>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 1;
        rqp.ty = 3;
        rqp.pc = new PrimitiveContent;
        var payload = "{\"m2m:cnt\":" + JSON.stringify(cnt) + "}";
        rqp.pc.anyOrAny.push(payload);
        return this.client.send(rqp).map(response => new ResourceResult<Cnt>(response, (response.rsc === 2001) ? response.pc.anyOrAny[0] : null));
    }

    public createContainer01(path: string, hierarchical?: boolean): Observable<ResourceResult<Cnt>> {
        var cnt: Cnt = new Cnt;
        return this.createContainer(path, cnt);
    }

    public createContainer02(path: string, rn: string, hierarchical?: boolean): Observable<ResourceResult<Cnt>> {
        var cnt: Cnt = new Cnt;
        cnt.rn = rn;
        return this.createContainer(path, cnt, hierarchical);
    }

    public retrieveContainer(path: string, hierarchical?: boolean): Observable<ResourceResult<Cnt>> {
        return this.retrieveResource(path, hierarchical).map(result => (result.resource instanceof Cnt ? new ResourceResult<Cnt>(result.response, result.resource) : new ResourceResult<Cnt>(result.response, null)));
    }

    // content instance
    public createContentInstance(path: string, cin: Cin, hierarchical?: boolean): Observable<ResourceResult<Cin>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 1;
        rqp.ty = 4;
        rqp.pc = new PrimitiveContent;
        var payload = "{\"m2m:cin\":" + JSON.stringify(cin) + "}";
        rqp.pc.anyOrAny.push(payload);
        return this.client.send(rqp).map(response => new ResourceResult<Cin>(response, (response.rsc === 2001) ? response.pc.anyOrAny[0] : null));
    }

    public createContentInstance01(path: string, content: string, hierarchical?: boolean): Observable<ResourceResult<Cin>> {
        let cin: Cin = new Cin;
        cin.con = content;
        return this.createContentInstance(path, cin, hierarchical);
    }

    public createContentInstance02(path: string, rn: string, content: string, hierarchical?: boolean): Observable<ResourceResult<Cin>> {
        let cin: Cin = new Cin;
        cin.con = content;
        cin.rn = rn;
        return this.createContentInstance(path, cin, hierarchical);
    }

    public retrieveContentInstance(path: string, hierarchical?: boolean): Observable<ResourceResult<Cin>> {
        return this.retrieveResource(path, hierarchical).map(result => (result.resource instanceof Cin ? new ResourceResult<Cin>(result.response, result.resource) : new ResourceResult<Cin>(result.response, null)));
    }
}
