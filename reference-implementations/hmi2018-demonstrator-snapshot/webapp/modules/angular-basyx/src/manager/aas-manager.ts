

import { AssetAdministrationShell, SubModel, Operation, Property, Event, Parameter } from '../resources/basic-resources';
import { ConnectedAssetAdministrationShell, ConnectedSubModel, ConnectedOperation, ConnectedProperty, ConnectedEvent } from '../resources/connected-resources';
import { Observable } from 'rxjs/Rx';

export interface AssetAdministrationShellManager {
    createAAS(aas: AssetAdministrationShell): Observable<ConnectedAssetAdministrationShell>;
    retrieveAAS(id: string): Observable<ConnectedAssetAdministrationShell>;
    retrieveAASAll(): Observable<ConnectedAssetAdministrationShell[]>;
    callOperation(aasId: string, submodelName: string, operationName: string, params: any[]): Observable<any>;
    getProperty(aasId: string, submodelName: string, propertyName: string): Observable<any>;
}