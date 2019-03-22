import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { Injectable } from '@angular/core';

@Injectable()
export class EventService { 
    private topics : Subject<any>[] = [];

    setTopic(topic: string, payload: any) {
        if (this.topics[topic] === undefined) {
            this.topics[topic] = new Subject<any>();
        }
        this.topics[topic].next(payload);
        // console.log('Broadcasting: ', payload);
    }

    unsetTopic(topic : string) {
        this.topics[topic].next();
    }

    observeTopic(topic : string) : Observable<any> {
        if (this.topics[topic] === undefined) {
            this.topics[topic] = new Subject<any>();
        }
        return this.topics[topic].asObservable();
    }

    constructor() { }

}