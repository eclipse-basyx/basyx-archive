import { ConnectedProperty } from 'angular-basyx';
// import { SmartControlResourcesUtil } from 'angular-basyx';
import { OneM2MResourceManager, OneM2MResourceExplorerService } from 'angular-onem2m';
import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { NodeM2M } from '../resources/nodeM2M';
import { OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';

@Component({
    selector: 'value-view',
    templateUrl: "value-view.component.html",
    styleUrls: ['value-view.component.scss']
})
export class ValueView implements OnInit {
    @Input()
    selectedNode: NodeM2M;

    lastValue: string;

    constructor(
        private smartcontrolManagerService: OneM2MAssetAdministrationShellManagerService
    ) {
    }

    getLastValue() {
        let uriParts : string[] = this.selectedNode.path.split("/");
        this.smartcontrolManagerService.getProperty(uriParts[uriParts.length-3], uriParts[uriParts.length-2], uriParts[uriParts.length-1]).subscribe( result => {
            this.lastValue = result;
        })
    }

    ngOnChanges(): void {
        this.getLastValue();
    }

    ngOnInit(): void {
        this.getLastValue();
    }

    stringify(variable: any) {
        return JSON.stringify(variable);
    }
}
