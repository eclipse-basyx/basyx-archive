import { BaseElement, AssetAdministrationShell, SubModel, Property, Event, Operation, Parameter } from "./basic-resources";
import { Observable } from 'rxjs/Observable';


export abstract class ConnectedAssetAdministrationShell extends AssetAdministrationShell {

    public addSubModel(subModel: ConnectedSubModel) {
        this._subModels.set(subModel.name, subModel);
        subModel.parent = this;
    }

    public get subModels(): Map<string, ConnectedSubModel> {
        var list: Map<string, ConnectedSubModel> = new Map<string, ConnectedSubModel>();
        this._subModels.forEach((value: SubModel, key: string) => {
            if (value instanceof ConnectedSubModel) {
                list.set(key, value);
            } else {
                throw Error("Expected object of type ConnectedSubModel! Got: " + value);
            }
        });
        return list;
    }
};

export abstract class ConnectedSubModel extends SubModel {

    public addOperation(operation: ConnectedOperation) {
        this._operations.set(operation.name, operation);
        operation.parent = this;
    }

    public get operations(): Map<string, ConnectedOperation> {
        var list: Map<string, ConnectedOperation> = new Map<string, ConnectedOperation>();
        this._operations.forEach((value: Operation, key: string) => {
            if (value instanceof ConnectedOperation) {
                list.set(key, value);
            } else {
                throw Error("Expected object of type ConnectedOperation! Got: " + value);
            }
        });
        return list;
    }


    public addProperty(property: ConnectedProperty) {
        this._properties.set(property.name, property);
        property.parent = this;
    }

    public get properties(): Map<string, ConnectedProperty> {
        var list: Map<string, ConnectedProperty> = new Map<string, ConnectedProperty>();
        this._properties.forEach((value: Property, key: string) => {
            if (value instanceof ConnectedProperty) {
                list.set(key, value);
            } else {
                throw Error("Expected object of type ConnectedProperty! Got: " + value);
            }
        });
        return list;
    }


    public addEvent(event: ConnectedEvent) {
        this._events.set(event.name, event);
        event.parent = this;
    }

    public get events(): Map<string, ConnectedEvent> {
        var list: Map<string, ConnectedEvent> = new Map<string, ConnectedEvent>();
        this._events.forEach((value: Event, key: string) => {
            if (value instanceof ConnectedEvent) {
                list.set(key, value);
            } else {
                throw Error("Expected object of type ConnectedEvent! Got: " + value);
            }
        });
        return list;
    }
};

export abstract class ConnectedProperty extends Property {
};

export abstract class ConnectedSingleProperty extends ConnectedProperty {
    abstract set(value: any, push?: boolean): Observable<Boolean>;
    abstract get(pull?: boolean): Observable<any>;
};

export abstract class ConnectedCollectionProperty extends ConnectedProperty {
    abstract set(key: string, value: any, push?: boolean): Observable<Boolean>;
    abstract get(key: string, pull?: boolean): Observable<any>;
    abstract getKeys(pull?: boolean): Observable<string[]>;
    abstract remove(key: string): Observable<Boolean>;
};

export abstract class ConnectedOperation extends Operation {
    abstract call(params: any[]): Observable<Boolean>;
};

export abstract class ConnectedEvent extends Event {
    abstract throw(value: any): void;
};
