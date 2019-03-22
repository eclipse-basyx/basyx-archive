
export enum DataType {
    REFERENCE,
    BOOLEAN,
    INTEGER,
    FLOAT,
    STRING,
    VOID,
}

export enum AssetKind {
    TYPE,
    INSTANCE
}

export class BaseElement {

    protected _parent: BaseElement;
    protected _assetKind: AssetKind;
    protected _id: string;

    public set parent(parent: BaseElement) {
        this._parent = parent;
    }

    public get parent(): BaseElement {
        return this._parent;
    }

    public set assetKind(assetKind: AssetKind) {
        this._assetKind = assetKind;
    }

    public get assetKind(): AssetKind {
        return this._assetKind;
    }

    public set id(id: string) {
        this._id = id;
    }

    public get id(): string {
        return this._id;
    }
}

export class AssetAdministrationShell extends BaseElement {

    protected _assetId: string;
    protected _assetTypeDefinition: string;
    protected _displayName: string;
    protected _subModels: Map<string, SubModel> = new Map();

    public set assetId(assetId: string) {
        this._assetId = assetId;
    }

    public get assetId(): string {
        return this._assetId;
    }

    public set assetTypeDefinition(assetTypeDefinition: string) {
        this._assetTypeDefinition = assetTypeDefinition;
    }

    public get assetTypeDefinition(): string {
        return this._assetTypeDefinition;
    }

    public set displayName(displayName: string) {
        this._displayName = displayName;
    }

    public get displayName(): string {
        return this._displayName;
    }

    public addSubModel(subModel: SubModel) {
        this._subModels.set(subModel.name, subModel);
        subModel.parent = this;
    }

    public get subModels(): Map<string, SubModel> {
        return this._subModels;
    }
}

export class SubModel extends BaseElement {

    protected _name: string;
    protected _typeDefinition: string;

    protected _operations: Map<string, Operation> = new Map();
    protected _properties: Map<string, Property> = new Map();
    protected _events: Map<string, Event> = new Map();

    public set name(name: string) {
        this._name = name;
    }

    public get name(): string {
        return this._name;
    }

    public set typeDefinition(typeDefinition: string) {
        this._typeDefinition = typeDefinition;
    }

    public get typeDefinition(): string {
        return this._typeDefinition;
    }

    public addOperation(operation: Operation) {
        this._operations.set(operation.name, operation);
        operation.parent = this;
    }

    public get operations(): Map<string, Operation> {
        return new Map(this._operations); // returns a copy
    }

    public addProperty(property: Property) {
        this._properties.set(property.name, property);
        property.parent = this;
    }

    public get properties(): Map<string, Property> {
        return new Map(this._properties); // returns a copy
    }

    public addEvent(event: Event) {
        this._events.set(event.name, event);
        event.parent = this;
    }

    public get events(): Map<string, Event> {
        return new Map(this._events); // returns a copy
    }
}

export class Event extends BaseElement {

    protected _name: string = undefined;
    protected _datatype: DataType = undefined;

    public set name(name: string) {
        this._name = name;
    }

    public get name(): string {
        return this._name;
    }

    public set datatype(datatype: DataType) {
        this._datatype = datatype;
    }

    public get datatype(): DataType {
        return this._datatype;
    }
}

export class Property extends BaseElement {

    protected _name: string = undefined;
    protected _datatype: DataType = undefined;
    protected _is_collection: boolean = undefined;
    protected _readable: boolean = undefined;
    protected _writeable: boolean = undefined;
    protected _eventable: boolean = undefined;

    public set name(name: string) {
        this._name = name;
    }

    public get name(): string {
        return this._name;
    }

    public get is_collection(): boolean {
        return this._is_collection;
    }

    public set is_collection(is_collection: boolean) {
        this._is_collection = is_collection;
    }

    public get datatype(): DataType {
        return this._datatype;
    }

    public set datatype(datatype: DataType) {
        this._datatype = datatype;
    }

    public get readable(): boolean {
        return this._readable;
    }

    public set readable(readable: boolean) {
        this._readable = readable;
    }

    public get writeable(): boolean {
        return this._writeable;
    }

    public set writeable(writeable: boolean) {
        this._writeable = writeable;
    }

    public get eventable(): boolean {
        return this._eventable;
    }

    public set eventable(eventable: boolean) {
        this._eventable = eventable;
    }
}

export class Operation extends BaseElement {

    protected _name: string = undefined;
    protected _parameters: Parameter[];
    protected _returnDatatype: DataType;

    public get parameters(): Parameter[] {
        return this._parameters;
    }

    public set parameters(parameters: Parameter[]) {
        this._parameters = parameters;
    }

    public get returnDatatype(): DataType {
        return this._returnDatatype;
    }

    public set returnDatatype(returnDatatype: DataType) {
        this._returnDatatype = returnDatatype;
    }

    public get name(): string {
        return this._name;
    }

    public set name(name: string) {
        this._name = name;
    }
}


export class Parameter extends BaseElement {

    protected _index: number;
    protected _datatype: DataType;
    protected _name: string;
    protected _value: any;

    public get index(): number {
        return this._index;
    }

    public set index(index: number) {
        this._index = index;
    }

    public get datatype(): DataType {
        return this._datatype;
    }

    public set datatype(datatype: DataType) {
        this._datatype = datatype;
    }

    public get name(): string {
        return this._name;
    }

    public set name(name: string) {
        this._name = name;
    }

    public get value(): any {
        return this._value;
    }

    public set value(value: any) {
        this._value = value;
    }
}
