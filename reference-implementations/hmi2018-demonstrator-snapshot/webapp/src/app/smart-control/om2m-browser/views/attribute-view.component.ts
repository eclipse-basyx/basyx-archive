import { Component, Input } from '@angular/core';
import { NodeM2M } from "../../../smart-control/om2m-browser/resources/nodeM2M";

@Component({
    selector: 'attribute-view',
    templateUrl: 'attribute-view.component.html',
    styleUrls: ['attribute-view.component.scss']
})
export class AttributeView {
    @Input()
    selectedNode: NodeM2M;

    hideContent: boolean = false;

    constructor() {
    }

    ngAfterViewInit() { }

    keys(): Array<any> {
        return Object.keys(this.selectedNode.m2mData);
    }

    stringify(variable: any) {
        return JSON.stringify(variable);
    }
}
