import { Rqp, Rsp } from "../resources/onem2m-resources";
import { Observable } from 'rxjs/Observable';

export interface OneM2MClient {
    start(config: any): void;
    stop(): void;
    createDefaultRequest(path: string, hierarchical?: boolean): Rqp;
    send(rqp: Rqp): Observable<Rsp>;
} 