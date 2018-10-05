import { Component, OnInit, Input } from '@angular/core';
import { SubModel } from 'angular-basyx';
import { ConnectedEvent } from 'angular-basyx';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'submodel-view',
    templateUrl: 'submodel-view.component.html',
    styleUrls: ['../view.scss', 'submodel-view.component.scss']
})

export class SubmodelViewComponent implements OnInit {
    @Input()
    subModel: SubModel;

    hideContent: boolean = false;

    private parameterInputValues: any[] = [];

    constructor(private modalService: NgbModal) { }

    ngOnInit() {
    }

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

    updateEvent(event: ConnectedEvent): void {
        //console.log("TODO"); // will be added when components with events are available for testing
    }

    openInfo(event, content) {
        event.stopPropagation(); // Dont open header
        this.modalService.open(content).result.then((result) => {
        }, (reason) => {
        });
    }
}
