import { Component, OnInit, Input } from '@angular/core';
import { SubModel } from 'angular-basyx';

@Component({
    selector: 'events-view',
    templateUrl: 'events-view.component.html',
    styleUrls: ['../view.scss', 'events-view.component.scss']
})

export class EventsViewComponent implements OnInit {
    @Input()
    subModel: SubModel;

    hideContent: boolean = false;

    constructor() { }

    ngOnInit() { }

    protected util_cache: Map<string, any> = new Map<string, any>();
    util_entries(key: string, map: Map<any, any>) { // this is an angular workaround        
        var value: any = Array.from(map.entries());
        if (this.util_cache.has(key) === false) {
            this.util_cache.set(key, value);
        } else {
            value = this.util_cache.get(key);
        }
        return value;
    }
}
