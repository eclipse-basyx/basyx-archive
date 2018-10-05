
import { BaseElement, AssetAdministrationShell, SubModel, Property, Event, Operation, Parameter } from "./basic-resources";
import { ConnectedAssetAdministrationShell, ConnectedSubModel, ConnectedSingleProperty, ConnectedCollectionProperty, ConnectedEvent, ConnectedOperation } from "./connected-resources";
import { OneM2MResourceManager, PrimitiveContent, Rqp, ResourceResult, Resource, Cnt, FilterCriteria } from 'angular-onem2m';
import { Observable } from 'rxjs/Observable';
import { ReplaySubject } from 'rxjs/ReplaySubject';


export class OneM2MConnectedAssetAdministrationShell extends ConnectedAssetAdministrationShell {

    public _m2m_ri = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;

    public constructor(_resourceManager: OneM2MResourceManager, m2m_ri: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri = m2m_ri;
    }
}


export class OneM2MConnectedSubModel extends ConnectedSubModel {

    public _m2m_ri = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;

    public constructor(_resourceManager: OneM2MResourceManager, m2m_ri: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri = m2m_ri;
    }
}


export class OneM2MConnectedEvent extends ConnectedEvent {
    public _m2m_ri_DATA = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;

    public constructor(_resourceManager: OneM2MResourceManager, m2m_ri_DATA: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri_DATA = m2m_ri_DATA;
    }

    throw(value: any) {
        this._resourceManager.createContentInstance01(this._m2m_ri_DATA, encodeURIComponent(JSON.parse(value)));
    }
}


export class OneM2MConnectedCollectionProperty extends ConnectedCollectionProperty {
    public _m2m_ri_DATA = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _m2m_la_DATA = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;

    public constructor(_resourceManager: OneM2MResourceManager, _m2m_ri_DATA: string, _m2m_la_DATA: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri_DATA = _m2m_ri_DATA;
        this._m2m_la_DATA = _m2m_la_DATA;
    }

    private stripHierarchical(address: string): string {
        var numOverheadParts: number = 3;
        var parts: string[] = address.split('/', numOverheadParts);
        var offset: number = numOverheadParts;
        for (var part of parts) {
            offset += part.length;
        }
        return address.substr(offset);
    }

    public getKeys(): Observable<string[]> {
        var subject: ReplaySubject<string[]> = new ReplaySubject<string[]>(1);

        var urlLa: string = this._m2m_la_DATA;
        var urlData: string = urlLa.substr(0, urlLa.length - 3);

        var rqp: Rqp = this._resourceManager.getClient().createDefaultRequest(urlData, false);
        rqp.op = 2;
        rqp.rcn = 5; // add children
        rqp.fc = new FilterCriteria;
        rqp.fc.lvl = 1;
        rqp.pc = new PrimitiveContent;

        this._resourceManager.getClient().send(rqp).subscribe(
            response => {
                if (response.rsc === 2000) {
                    var cnt: Cnt = response.pc.anyOrAny[0] as Cnt;
                    var keys: string[] = new Array<string>();
                    for (var elem of cnt.ch) {
                        keys.push(elem.nm);
                    }
                    subject.next(keys);
                    subject.complete();
                } else {
                    subject.next(null);
                    subject.complete();
                }
            },
            error => {
                subject.error(error)
            }
        );
        return subject.asObservable();
    }

    remove(key: string): Observable<Boolean> {
        if (this.is_collection) {
            var url = this._m2m_la_DATA;
            url = url.substr(0, url.length - 2) + key;
            return this._resourceManager.deleteResource(this.stripHierarchical(url), true).map(result => (result.response.rsc === 2002 ? true : false));
        } else {
            throw Error("Illegal state");
        }
    }

    get(key: string): Observable<any> {
        if (this.is_collection) {
            var url = this._m2m_la_DATA;
            url = url.substr(0, url.length - 2) + key + "/la";
            return this._resourceManager.retrieveContentInstance(this.stripHierarchical(url), true).map(result => (result.response.rsc === 2000 ? JSON.parse(decodeURIComponent(result.resource.con)) : undefined));
        } else {
            throw Error("Illegal state");
        }
    }

    set(key: string, value: any): Observable<Boolean> {
        var subject: ReplaySubject<Boolean> = new ReplaySubject<Boolean>(1);
        if (this.is_collection) {
            if (key == null || key == "" || !(new RegExp(/^[$A-Z_][0-9A-Z_$]*$/i).test(key))) {
                throw Error("Illegal argument (Empty key or invalid key [must conform to a valid identifier name] is not allowed).");
            }
            var urlLa: string = this._m2m_la_DATA;
            var urlData: string = urlLa.substr(0, urlLa.length - 3);
            var urlKey: string = urlData + "/" + key;
            this._resourceManager.retrieveContainer(this.stripHierarchical(urlKey), true).subscribe(
                result => {
                    this._resourceManager.createContentInstance01(this.stripHierarchical(urlKey), encodeURIComponent(JSON.stringify(value)), true).subscribe(
                        result => {
                            if (result.response.rsc === 2001) {
                                subject.next(true);
                                subject.complete();
                            }
                        },
                        error => {
                            subject.error(error);
                        });
                },
                error => {
                    if (error.rsc === 4004) { // not found                    
                        this._resourceManager.createContainer02(this.stripHierarchical(urlData), key, true).subscribe(
                            result => {
                                this._resourceManager.createContentInstance01(this.stripHierarchical(urlKey), encodeURIComponent(JSON.stringify(value)), true).subscribe(
                                    result => {
                                        if (result.response.rsc === 2001) {
                                            subject.next(true);
                                            subject.complete();
                                        }
                                    },
                                    error => {
                                        subject.error(error);
                                    });
                            },
                            error => {
                                subject.error(error);
                            });
                    }
                });
            return subject.asObservable();
        } else {
            throw Error("Illegal state");
        }
    }
}


export class OneM2MConnectedSingleProperty extends ConnectedSingleProperty {
    public _m2m_ri_DATA = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _m2m_la_DATA = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;
    private _localValue = undefined;

    public constructor(_resourceManager: OneM2MResourceManager, _m2m_ri_DATA: string, _m2m_la_DATA: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri_DATA = _m2m_ri_DATA;
        this._m2m_la_DATA = _m2m_la_DATA;
    }

    public get localValue(): any {
        return this._localValue;
    }

    private stripHierarchical(address: string): string {
        var numOverheadParts: number = 3;
        var parts: string[] = address.split('/', numOverheadParts);
        var offset: number = numOverheadParts;
        for (var part of parts) {
            offset += part.length;
        }
        return address.substr(offset);
    }

    get(): Observable<any> {
        if (this.is_collection) {
            throw new Error("Illegal state");
        } else {
            if (this._m2m_la_DATA === undefined) {
                let subject: ReplaySubject<any> = new ReplaySubject<any>(1);
                this._resourceManager.retrieveResource(this._m2m_ri_DATA).subscribe(
                    result => {
                        if (result.response.rsc === 2000) {
                            return this._resourceManager.retrieveContentInstance(this.stripHierarchical(this._m2m_la_DATA), true).subscribe(
                                result => {
                                    if (result.response.rsc === 2000) {
                                        subject.next(JSON.parse(decodeURIComponent(result.resource.con)));
                                        subject.complete();
                                    }
                                },
                                error => {
                                    subject.error(error);
                                }
                            );
                        }
                    },
                    error => {
                        subject.error(error);
                    });
                return subject.asObservable();
            } else {
                return this._resourceManager.retrieveContentInstance(this.stripHierarchical(this._m2m_la_DATA), true).map(result => (result.response.rsc === 2000 ? this._localValue = JSON.parse(decodeURIComponent(result.resource.con)) : undefined));
            }
        }
    }

    set(value: any): Observable<Boolean> {
        var subject: ReplaySubject<Boolean> = new ReplaySubject<Boolean>(1);
        if (this.is_collection) {
            throw new Error("Illegal state");
        } else {
            return this._resourceManager.createContentInstance01(this._m2m_ri_DATA, encodeURIComponent(JSON.stringify(value))).map(result => (result.response.rsc === 2001) ? true : false);
        }
    }
}


export class OneM2MConnectedOperation extends ConnectedOperation {
    public _m2m_ri = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _m2m_ri_REQ = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _m2m_ri_PROC = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _m2m_ri_RESP = undefined; /* should be protected + friend to other classes, however, this is not possible in typescript. */
    public _resourceManager: OneM2MResourceManager;

    public constructor(_resourceManager: OneM2MResourceManager, m2m_ri: string, m2m_ri_REQ: string, m2m_ri_PROC: string, m2m_ri_RESP: string) {
        super();
        this._resourceManager = _resourceManager;
        this._m2m_ri = m2m_ri;
        this._m2m_ri_REQ = m2m_ri_REQ;
        this._m2m_ri_PROC = m2m_ri_PROC;
        this._m2m_ri_RESP = m2m_ri_RESP;
    }

    public call(params: any[]): Observable<any> {
        let subject: ReplaySubject<any> = new ReplaySubject<any>(1);
        this._resourceManager.createContentInstance01(this._m2m_ri_REQ, encodeURIComponent(JSON.stringify(params))).subscribe(
            result1 => {
                if (result1.response.rsc === 2001) {
                    subject.next(true); // TODO mschoeffler catch actual result
                    subject.complete();
                }
            },
            error => {
                subject.error(error);
            });
        return subject.asObservable();
    }
}
