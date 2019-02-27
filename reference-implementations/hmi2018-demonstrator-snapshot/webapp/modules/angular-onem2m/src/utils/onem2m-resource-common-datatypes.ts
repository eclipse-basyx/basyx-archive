import { Cnt, Cin, Rqp, Rsp, PrimitiveContent, Resource } from '../resources/onem2m-resources';


export class ResourceResult<T extends Resource> {
    response: Rsp;
    resource: T;

    constructor(rsp: Rsp, res: T) {
        this.response = rsp;
        this.resource = res;
    }
}

export class ResourcesResult<T extends Resource[]> {
    response: Rsp;
    resources: T;

    constructor(rsp: Rsp, res: T) {
        this.response = rsp;
        this.resources = res;
    }
}

export class ResourcesExtendedResult<T extends Resource[]> {
    responses: Rsp[] = [];
    resources: T;

    constructor() {
    }
}

export class ResourceExtendedResult<T extends Resource> {
    responses: Rsp[] = [];
    resource: T;

    constructor() {
    }
}


export class DataResult<T> {
    response: Rsp;
    data: T;
    constructor(rsp: Rsp, data: T) {
        this.response = rsp;
        this.data = data;
    }
}
