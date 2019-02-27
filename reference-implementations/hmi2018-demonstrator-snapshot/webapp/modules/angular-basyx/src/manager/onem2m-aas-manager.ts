
import { ResourceExtendedResult, OneM2MResourceManager, OneM2MResourceExplorer, Cin } from 'angular-onem2m';
import { AssetAdministrationShell, SubModel, Operation, Property, Event, Parameter } from '../resources/basic-resources';
import { ConnectedAssetAdministrationShell, ConnectedSubModel, ConnectedOperation, ConnectedProperty, ConnectedEvent } from '../resources/connected-resources';
import { OneM2MConnectedAssetAdministrationShell, OneM2MConnectedSubModel, OneM2MConnectedOperation, OneM2MConnectedSingleProperty, OneM2MConnectedCollectionProperty, OneM2MConnectedEvent } from '../resources/onem2m-connected-resources';
import { ResourcesUtil } from '../resources/basic-resources-util';
import { OneM2MResourcesUtil } from 'angular-onem2m';

import { Cnt, Ae, Resource } from 'angular-onem2m'
import { Observable } from 'rxjs/Rx';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { AssetAdministrationShellManager } from './aas-manager';


export class OneM2MAssetAdministrationShellManager implements AssetAdministrationShellManager {

    private _resourceManager: OneM2MResourceManager;
    private _resourceExplorer: OneM2MResourceExplorer;

    constructor(resourceManager: OneM2MResourceManager) {
        this._resourceManager = resourceManager;
        this._resourceExplorer = new OneM2MResourceExplorer(this._resourceManager.getClient());
    }

    public retrieveOrCreateAAS(aas: AssetAdministrationShell): Observable<ConnectedAssetAdministrationShell> {
        let subject: ReplaySubject<ConnectedAssetAdministrationShell> = new ReplaySubject<ConnectedAssetAdministrationShell>(1);
        this.retrieveAAS(aas.id).subscribe(
            result => {
                if (result !== null) {
                    subject.next(result);
                    subject.complete();
                } else {
                    this.createAAS(aas).subscribe(
                        resultInner => {
                            subject.next(resultInner);
                            subject.complete();
                        },
                        error => {
                            subject.error(error);
                        }
                    );
                }
            },
            error => {
                this.createAAS(aas).subscribe(
                    resultInner => {
                        subject.next(resultInner);
                        subject.complete();
                    },
                    error => {
                        subject.error(error);
                    }
                );
            }
        );

        return subject.asObservable();
    }

    public callOperation(aasId: string, submodelName: string, operationName: string, params: any[]): Observable<any> {
        let subject: ReplaySubject<any> = new ReplaySubject<any>(1);
        this._resourceManager.createContentInstance01(aasId + "/" + submodelName + "/" + operationName + "/REQ", encodeURIComponent(JSON.stringify(params)), true).subscribe(
            result1 => {
                if (result1.response.rsc === 2001) {
                    subject.next(true); // TODO catch actual result
                    subject.complete();
                } else {
                    subject.next(null);
                    subject.complete();
                }
            },
            error => {
                subject.error(error);
            });
        return subject.asObservable();
    }

    public getProperty(aasId: string, submodelName: string, propertyName: string): Observable<any> {
        let subject: ReplaySubject<any> = new ReplaySubject<any>(1);
        this._resourceManager.retrieveContentInstance(aasId + "/" + submodelName + "/" + propertyName + "/DATA/la", true).subscribe(
            result1 => {
                if (result1.response.rsc === 2000) {
                    subject.next(JSON.parse(decodeURIComponent(result1.resource.con)));
                    subject.complete();
                } else {
                    subject.next(null);
                    subject.complete();
                }
            },
            error => {
                subject.error(error);
            });
        return subject.asObservable();
    }


    public deleteAAS(id: string): Observable<Boolean> {
        let subject: ReplaySubject<Boolean> = new ReplaySubject<Boolean>();
        this._resourceManager.deleteResource(id, true).subscribe(
            result => {
                if (result.data == true) {
                    subject.next(true);
                } else {
                    subject.next(false);
                }
                subject.complete();
            },
            error => {
                subject.error(error);
            });
        return subject.asObservable();
    }

    public createAAS(aas: AssetAdministrationShell): Observable<ConnectedAssetAdministrationShell> {
        let subject: ReplaySubject<ConnectedAssetAdministrationShell> = new ReplaySubject<ConnectedAssetAdministrationShell>(1);
        let aeAAS: Ae = new Ae;
        aeAAS.rn = aas.id;
        aeAAS.api = aas.id;
        aeAAS.rr = false;

        aeAAS.lbl.push("basys:aas");
        aeAAS.lbl.push("id:" + aas.id);
        aeAAS.lbl.push("atd:" + aas.assetTypeDefinition);
        aeAAS.lbl.push("dn:" + aas.displayName);
        aeAAS.lbl.push("ak:" + ResourcesUtil.convertAssetKindToStr(aas.assetKind));
        aeAAS.lbl.push("aid:" + aas.assetId);

        this._resourceManager.createApplicationEntity('', aeAAS).subscribe(
            result => {
                if (result.response.rsc === 2001) {
                    let caas: OneM2MConnectedAssetAdministrationShell = new OneM2MConnectedAssetAdministrationShell(this._resourceManager, result.resource.ri);
                    let obsCServ: Observable<ConnectedSubModel>[] = [];
                    aas.subModels.forEach((subModel, key) => {
                        if (subModel instanceof SubModel) {
                            obsCServ.push(this.createAndAddSubModel(caas, result.resource, key, subModel));
                        } else {
                            // TODO
                        }
                    });
                    if (obsCServ.length > 0) {
                        Observable.forkJoin(obsCServ).subscribe(
                            cservices => {
                                subject.next(caas);
                                subject.complete();
                            },
                            error => {
                                subject.error(error)
                            });
                    } else {
                        subject.next(caas);
                        subject.complete();
                    }
                } else {
                    subject.next(null);
                    subject.complete();
                }
            },
            error => {
                subject.error(error);
            }
        );
        return subject.asObservable();
    }

    protected createAndAddSubModel(parent: OneM2MConnectedAssetAdministrationShell, parentResource: Resource, name: string, subModel: SubModel): Observable<ConnectedSubModel> {
        let subject: ReplaySubject<ConnectedSubModel> = new ReplaySubject<ConnectedSubModel>(1);

        // Create Connected Service

        // Cnt
        let cntServ: Cnt = new Cnt;
        cntServ.rn = name;
        cntServ.lbl.push("basys:sm");
        if (subModel.id !== undefined) {
            cntServ.lbl.push("id:" + subModel.id);
        }
        cntServ.lbl.push("td:" + subModel.typeDefinition);
        this._resourceManager.createContainer(parentResource.ri, cntServ, false).subscribe(
            result => {
                let cSubModel: OneM2MConnectedSubModel = new OneM2MConnectedSubModel(parent._resourceManager, result.resource.ri);
                cSubModel.id = subModel.id;
                cSubModel.name = subModel.name;
                cSubModel.assetKind = subModel.assetKind;
                parent.addSubModel(cSubModel);

                let obsCPropOpEvt: Observable<any>[] = [];
                subModel.properties.forEach((property) => {
                    if (property instanceof Property) {
                        obsCPropOpEvt.push(this.createAndAddProperty(cSubModel, result.resource, property.name, property));
                    } else {
                        // TODO
                    }
                });
                if (obsCPropOpEvt.length > 0) {
                    Observable.forkJoin(obsCPropOpEvt).subscribe(
                        cservices => {
                            subject.next(cSubModel);
                            subject.complete();
                        },
                        error => {
                            subject.error(error);
                        });
                } else {
                    subject.next(cSubModel);
                    subject.complete();
                }
            },
            error => {
                subject.error(error);
            });
        return subject.asObservable();
    }

    protected createAndAddProperty(parent: OneM2MConnectedSubModel, parentCnt: Cnt, name: string, property: Property): Observable<ConnectedProperty> {
        let subject: ReplaySubject<ConnectedProperty> = new ReplaySubject<ConnectedProperty>(1);

        // Cnt
        let cntProperty: Cnt = new Cnt;
        cntProperty.rn = name;
        cntProperty.lbl.push("basys:prop");
        if (property.id !== undefined) {
            cntProperty.lbl.push("id:" + property.id);
        }
        cntProperty.lbl.push("dty:" + ResourcesUtil.convertDataTypeToStr(property.datatype));
        if (property.readable) {
            cntProperty.lbl.push("rd");
        }
        if (property.writeable) {
            cntProperty.lbl.push("wd");
        }
        if (property.eventable) {
            cntProperty.lbl.push("evt");
        }
        if (property.is_collection) {
            cntProperty.lbl.push("col:map");
        }

        this._resourceManager.createContainer(parentCnt.ri, cntProperty, false).subscribe(
            result => {
                let cntData: Cnt = new Cnt;
                cntData.rn = "DATA";
                this._resourceManager.createContainer(result.resource.ri, cntData, false).subscribe(
                    resultInner => {

                        let cproperty: ConnectedProperty = null;
                        if (property.is_collection) {
                            cproperty = new OneM2MConnectedCollectionProperty(parent._resourceManager, resultInner.resource.ri, resultInner.resource.la);
                        } else {
                            cproperty = new OneM2MConnectedSingleProperty(parent._resourceManager, resultInner.resource.ri, resultInner.resource.la);
                        }

                        cproperty.datatype = property.datatype;
                        cproperty.is_collection = property.is_collection;
                        cproperty.readable = property.readable, // TODO MISTAKE?
                            cproperty.writeable = property.writeable;
                        cproperty.eventable = property.eventable;
                        cproperty.id = property.id;
                        cproperty.name = property.name;
                        parent.addProperty(cproperty);

                        subject.next(cproperty);
                        subject.complete();
                    },
                    error => {
                        subject.error(error);
                    });
            },
            error => {
                subject.error(error);
            });

        return subject.asObservable();
    }

    private stripHierarchical(address: string): string {
        let numOverheadParts: number = 3;
        let parts: string[] = address.split('/', numOverheadParts);
        let offset: number = numOverheadParts;
        for (let part of parts) {
            offset += part.length;
        }
        return address.substr(offset);
    }

    public retrieveAASAll(): Observable<ConnectedAssetAdministrationShell[]> {
        let subject: ReplaySubject<ConnectedAssetAdministrationShell[]> = new ReplaySubject<ConnectedAssetAdministrationShell[]>(1);
        this._resourceExplorer.findResourcesWithLabels01('', [encodeURIComponent('basys:aas')]).subscribe(
            result => {
                let shells: ConnectedAssetAdministrationShell[] = [];
                let obs: Observable<ResourceExtendedResult<Resource>>[] = [];
                for (let uri of result.data) {
                    obs.push(this._resourceExplorer.retrieveResourceWithChildrenRecursive(this.stripHierarchical(uri.toString()), true));
                }
                if (obs.length > 0) {
                    Observable.forkJoin(obs).subscribe(
                        resultsInner => {
                            for (let resultInner of resultsInner) {
                                if (resultInner.resource instanceof Ae) {
                                    let caas: ConnectedAssetAdministrationShell = this.mapAeToAAS(resultInner.resource as Ae, true);
                                    shells.push(caas);
                                }
                            }
                            subject.next(shells);
                            subject.complete();
                        },
                        error => {
                            subject.error(error);
                        });
                } else {
                    subject.next(shells);
                    subject.complete();
                }

            },
            error => {
                subject.error(error);
            }
        );

        return subject.asObservable();;
    }

    public retrieveAAS(id: string): Observable<ConnectedAssetAdministrationShell> {
        let subject: ReplaySubject<ConnectedAssetAdministrationShell> = new ReplaySubject<ConnectedAssetAdministrationShell>(1);
        this._resourceExplorer.findResourcesWithLabels01('', [encodeURIComponent('id:' + id)]).subscribe(
            result => {
                if (result.data.length === 0) {
                    subject.next(null);
                    subject.complete();
                    return;
                } else {
                    for (let uri of result.data) {
                        this._resourceExplorer.retrieveResourceWithChildrenRecursive(this.stripHierarchical(uri.toString()), true).subscribe(
                            result => {
                                if (result.resource instanceof Ae) {
                                    let caas: ConnectedAssetAdministrationShell = this.mapAeToAAS(result.resource as Ae, true);
                                    subject.next(caas);
                                    subject.complete();
                                    return;
                                }
                            },
                            error => {
                                subject.error(error);
                            }
                        );
                    }
                }
            },
            error => {
                subject.error(error);
            }
        );
        return subject.asObservable();
    }

    private mapCntToAAS(cnt: Cnt, recursive: boolean = true): OneM2MConnectedAssetAdministrationShell {
        let caas: OneM2MConnectedAssetAdministrationShell = new OneM2MConnectedAssetAdministrationShell(this._resourceManager, null);
        caas.id = this.extractLabel(cnt.lbl, "id");
        caas.assetId = this.extractLabel(cnt.lbl, "aid");
        caas.assetTypeDefinition = this.extractLabel(cnt.lbl, "atd");
        caas.displayName = this.extractLabel(cnt.lbl, "dn");
        caas.assetKind = ResourcesUtil.convertStrToAssetKind(this.extractLabel(cnt.lbl, "ak"));
        if (recursive === true) {
            this.mapCAASResourceChildren(OneM2MResourcesUtil.getChildrenArray(cnt), caas);
        }
        return caas;
    }


    private mapAeToAAS(ae: Ae, recursive: boolean = true): OneM2MConnectedAssetAdministrationShell {
        let caas: OneM2MConnectedAssetAdministrationShell = new OneM2MConnectedAssetAdministrationShell(this._resourceManager, null);
        caas.id = this.extractLabel(ae.lbl, "id");
        caas.assetId = this.extractLabel(ae.lbl, "aid");
        caas.assetTypeDefinition = this.extractLabel(ae.lbl, "atd");
        caas.displayName = this.extractLabel(ae.lbl, "dn");
        caas.assetKind = ResourcesUtil.convertStrToAssetKind(this.extractLabel(ae.lbl, "ak"));
        if (recursive === true) {
            this.mapCAASResourceChildren(OneM2MResourcesUtil.getChildrenArray(ae), caas);
        }
        return caas;
    }


    private mapCAASResourceChildren(arr: any[], caas: OneM2MConnectedAssetAdministrationShell, recursive: boolean = true) {
        for (let elem of arr) {

            let resource: Resource = elem as Resource;
            switch (this.extractLabel(resource.lbl, 'basys')) {
                case "sm": {
                    this.mapCntToSubModel(elem as Cnt, caas, true);
                    break;
                }
                default: {
                    console.log("Unsupported type: " + elem);
                }
            }
        }
    }

    private mapCntToSubModel(cnt: Cnt, parent: ConnectedAssetAdministrationShell, recursive: boolean = true): OneM2MConnectedSubModel {
        let csubmodel: OneM2MConnectedSubModel = new OneM2MConnectedSubModel(this._resourceManager, null);
        csubmodel.id = this.extractLabel(cnt.lbl, "id");
        csubmodel.name = cnt.rn;
        csubmodel.assetKind = ResourcesUtil.convertStrToAssetKind(this.extractLabel(cnt.lbl, "ak"));
        csubmodel.typeDefinition = this.extractLabel(cnt.lbl, "td");

        parent.addSubModel(csubmodel);
        if (recursive === true) {
            for (let elem of OneM2MResourcesUtil.getChildrenArray(cnt)) {
                let resource: Resource = elem as Resource;
                switch (this.extractLabel(resource.lbl, 'basys')) {
                    case "op": {
                        this.mapCntToOperation(elem as Cnt, csubmodel, recursive);
                        break;
                    }
                    case "prop": {
                        this.mapCntToProperty(elem as Cnt, csubmodel, recursive);
                        break;
                    }
                    case "evt": {
                        this.mapCntToEvent(elem as Cnt, csubmodel, recursive);
                        break;
                    }
                    // TODO events
                    default: {
                        console.log("Unsupported type: " + elem);
                    }
                }
            }
        }
        return csubmodel;
    }


    private mapCntToProperty(cnt: Cnt, parent: OneM2MConnectedSubModel, recursive: boolean = true): ConnectedProperty {
        let id: string = this.extractLabel(cnt.lbl, "id");
        let dty: string = this.extractLabel(cnt.lbl, "dty");

        let wr: string = this.extractLabel(cnt.lbl, "wr");
        let rd: string = this.extractLabel(cnt.lbl, "rd");
        let evt: string = this.extractLabel(cnt.lbl, "ev");
        let col: string = this.extractLabel(cnt.lbl, "col");

        let cop: ConnectedProperty = null;

        let m2m_ri_DATA: string = null;
        let m2m_la_DATA: string = null;

        for (let elem of OneM2MResourcesUtil.getChildrenArray(cnt)) {
            if (elem instanceof Cnt) {
                if (elem.rn === "DATA") {
                    m2m_ri_DATA = elem.ri;
                    m2m_la_DATA = elem.la;
                }
            }
        }


        if (col != null) {
            cop = new OneM2MConnectedCollectionProperty(this._resourceManager, m2m_ri_DATA, m2m_la_DATA);
            cop.is_collection = true;
        } else {
            cop = new OneM2MConnectedSingleProperty(this._resourceManager, m2m_ri_DATA, m2m_la_DATA);
            cop.is_collection = false;
        }

        cop.datatype = ResourcesUtil.convertStrToDataType(dty);
        cop.eventable = evt != null;
        cop.readable = rd != null;
        cop.writeable = wr != null;
        cop.id = id;
        cop.name = cnt.rn;
        parent.addProperty(cop);

        return cop;
    }

    private mapCntToEvent(cnt: Cnt, parent: ConnectedSubModel, recursive: boolean = true): ConnectedEvent {
        let id: string = this.extractLabel(cnt.lbl, "id");
        let dty: string = this.extractLabel(cnt.lbl, "dty");
        let m2m_ri_DATA: string = null;
        for (let elem of OneM2MResourcesUtil.getChildrenArray(cnt)) {
            if (elem instanceof Cnt) {
                if (elem.rn === "DATA") {
                    m2m_ri_DATA = elem.ri;
                }
            }
        }

        let ce: OneM2MConnectedEvent = new OneM2MConnectedEvent(this._resourceManager, m2m_ri_DATA);
        ce.name = cnt.rn;
        ce.id = id;
        ce.datatype = ResourcesUtil.convertStrToDataType(dty);
        parent.addEvent(ce);
        return ce;
    }

    private mapCntToOperation(cnt: Cnt, parent: ConnectedSubModel, recursive: boolean = true): ConnectedOperation {
        let id: string = this.extractLabel(cnt.lbl, "id");
        let rdty: string = this.extractLabel(cnt.lbl, "rdty");
        let parlen: number = Number(this.extractLabel(cnt.lbl, "par-len"));
        let parameters: Parameter[] = [];
        for (let i = 0; i < parlen; ++i) {
            let nm: string = this.extractLabel(cnt.lbl, "par-" + String(i) + "-nm");
            let dty: string = this.extractLabel(cnt.lbl, "par-" + String(i) + "-dty");
            let par: Parameter = new Parameter();
            par.index = i;
            par.datatype = ResourcesUtil.convertStrToDataType(dty);
            par.name = nm;
            parameters.push(par);
        }
        let m2m_ri_REQ: string = null;
        let m2m_ri_PROC: string = null;
        let m2m_ri_RESP: string = null;
        for (let elem of OneM2MResourcesUtil.getChildrenArray(cnt)) {
            if (elem instanceof Cnt) {
                let childCnt: Cnt = elem as Cnt;
                if (childCnt.rn === "REQ") {
                    m2m_ri_REQ = childCnt.ri;
                } else if (childCnt.rn === "PROC") {
                    m2m_ri_PROC = childCnt.ri;
                } else if (childCnt.rn === "RESP") {
                    m2m_ri_RESP = childCnt.ri;
                }
            }
        }
        let cop: ConnectedOperation = new OneM2MConnectedOperation(this._resourceManager, cnt.ri, m2m_ri_REQ, m2m_ri_PROC, m2m_ri_RESP);
        if (id != null) {
            cop.id = id;
        }
        cop.name = cnt.rn;
        cop.parameters = parameters;
        cop.returnDatatype = ResourcesUtil.convertStrToDataType(rdty);
        parent.addOperation(cop);
        return cop;
    }

    private extractLabel(lbl: string[], key: string): string {
        for (let label of lbl) {
            if (label.startsWith(key)) {
                return label.substr(key.length + 1);
            }
        }
        return null;
    }

}