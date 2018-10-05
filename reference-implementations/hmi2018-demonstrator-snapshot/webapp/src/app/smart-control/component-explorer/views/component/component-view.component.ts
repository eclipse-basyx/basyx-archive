import { Component, Input } from '@angular/core';
import { ConnectedAssetAdministrationShell, OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'component-view',
    templateUrl: 'component-view.component.html',
    styleUrls: ['../view.scss', 'component-view.component.scss']
})
export class ComponentViewComponent {
    @Input('caas') caas: ConnectedAssetAdministrationShell;

    hideContent: boolean = true;

    constructor(
        private aasManager: OneM2MAssetAdministrationShellManagerService,
        private modalService: NgbModal) {
    }

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

    openInfo(event, content) {
        event.stopPropagation(); // Dont open header
        this.modalService.open(content).result.then((result) => {
        }, (reason) => {
        });
    }
}
