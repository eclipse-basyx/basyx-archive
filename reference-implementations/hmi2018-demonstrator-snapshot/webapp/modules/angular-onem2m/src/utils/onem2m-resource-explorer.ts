import { OneM2MClient } from '../clients/onem2m-client';
import { FilterCriteria, Cnt, Cin, Rqp, Rsp, PrimitiveContent, Resource } from '../resources/onem2m-resources';
import { DataResult, ResourcesResult, ResourceExtendedResult } from './onem2m-resource-common-datatypes';
import { OneM2MResourcesUtil } from './../resources/onem2m-resources-util';
import 'rxjs/add/operator/map'
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { Observable } from 'rxjs/Rx';


export class OneM2MResourceExplorer {
    private client: OneM2MClient;

    constructor(client: OneM2MClient) {
        this.client = client;
    }

    public retrieveResourceUrils(path: string, hierarchical?: boolean): Observable<DataResult<string[]>> {
        let subject = new ReplaySubject<any>(1);

        let rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 2;
        rqp.fc = new FilterCriteria;
        rqp.fc.fu = 1;
        rqp.drt = 1;
        rqp.pc = new PrimitiveContent;

        this.client.send(rqp).subscribe(
            response => {
                let content = null;
                if (response.rsc === 2000) {
                    content = response.pc.anyOrAny[0];
                }
                subject.next(new DataResult<string[]>(response, content));
                subject.complete();
            },
            error => {
                subject.error(error);
            });

        return subject.asObservable();
    }

    public findResourcesWithLabels01(path: string, labels: string[], hierarchical?: boolean): Observable<DataResult<String[]>> {
        var rqp: Rqp = this.client.createDefaultRequest(path, hierarchical);
        rqp.op = 2;
        rqp.fc = new FilterCriteria;
        rqp.fc.fu = 1;
        rqp.fc.lbl = labels;
        rqp.drt = 1;
        rqp.pc = new PrimitiveContent;
        return this.client.send(rqp).map(response => new DataResult<String[]>(response, (response.rsc === 2000) ? response.pc.anyOrAny[0] : null),
            error => error); // should have only a signle result
    }


    public retrieveResourceWithChildrenRecursive(path: string, hierarchical?: boolean, childDepth?: number): Observable<ResourceExtendedResult<Resource>> {
        if (hierarchical === undefined || hierarchical === null) {
            hierarchical = true;
        }
        var subject: ReplaySubject<ResourceExtendedResult<Resource>> = new ReplaySubject<ResourceExtendedResult<Resource>>(1);

        var rqp: Rqp = null;
        try {
            rqp = this.client.createDefaultRequest(path, hierarchical);
        } catch (e) {
            subject.error("Could not make request (is client started?)");
            return subject;
        }
        rqp.op = 2;
        rqp.rcn = 4; // add children
        rqp.fc = new FilterCriteria();
        rqp.fc.lvl = 1;
        rqp.pc = new PrimitiveContent;

        this.client.send(rqp).subscribe(
            response => {
                let rer: ResourceExtendedResult<Resource> = new ResourceExtendedResult<Resource>();
                rer.responses.push(response);
                if (response.rsc === 2000) {
                    let res: Resource = response.pc.anyOrAny[0];
                    rer.resource = res;
                    var obs: Observable<ResourceExtendedResult<Resource>>[] = [];
                    let resChilds: Resource[] = OneM2MResourcesUtil.getChildrenArray(res);
                    if (resChilds !== null) {
                        for (var elem of resChilds) {
                            let elemChilds: Resource[] = OneM2MResourcesUtil.getChildrenArray(elem);
                            if (elemChilds !== null) {
                                let childRes: Resource = elem;
                                let childResChilds: Resource[] = OneM2MResourcesUtil.getChildrenArray(childRes)
                                if (childResChilds.length === 0) {
                                    // watch out for new childs
                                    if (childDepth === undefined) {
                                        obs.push(this.retrieveResourceWithChildrenRecursive(childRes.ri, false));
                                    } else if (childDepth !== 0) {
                                        obs.push(this.retrieveResourceWithChildrenRecursive(childRes.ri, false, childDepth - 1));
                                    }
                                }
                            }
                        }
                    }
                    var i: number = 0;
                    if (obs.length > 0) {
                        Observable.forkJoin(obs).subscribe(
                            resultsInner => {
                                for (var resultInner of resultsInner) {
                                    rer.responses = rer.responses.concat(resultInner.responses);
                                    OneM2MResourcesUtil.addChildToParent(rer.resource, resultInner.resource);
                                }
                                subject.next(rer);
                                subject.complete();
                            },
                            error => {
                                subject.error(rer);
                            });
                    } else {
                        subject.next(rer);
                        subject.complete();
                    }
                } else {
                    rer.resource = null;
                    subject.next(rer);
                    subject.complete();
                }
            },
            error => {
                let rer: ResourceExtendedResult<Resource> = new ResourceExtendedResult<Resource>();
                rer.responses.push(error);
                rer.resource = null;
                subject.error(rer);
            });
        return subject.asObservable();
    }
}