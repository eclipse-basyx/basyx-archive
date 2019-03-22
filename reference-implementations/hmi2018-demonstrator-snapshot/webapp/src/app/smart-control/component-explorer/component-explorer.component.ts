import { Component } from '@angular/core';
import { ConnectedAssetAdministrationShell, OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';

import { SmartControlService } from '../smart-control.service';
import { EventService } from '../../shared/services/smart-control/event-service.service';
import { HeadlineService } from '../common/headline/headline.service';

@Component({
    selector: 'component-explorer',
    templateUrl: 'component-explorer.component.html',
    styleUrls: ['component-explorer.component.scss', '../smart-control.component.scss']
})
export class ComponentExplorerComponent {
    filterValue: string = '';
    refreshing: boolean = false;

    subscription1: any;
    subscription2: any;

    constructor(
        private aasManagerService: OneM2MAssetAdministrationShellManagerService,
        private headlineService: HeadlineService,
        private smartControlService: SmartControlService,
        private eventService: EventService) {
    }

    ngOnInit() {
        this.headlineService.setHeadline('Component Explorer', 'list');
        this.refresh();


        this.subscription1 = this.eventService.observeTopic(SmartControlService.COMPONENTS_UPDATE_STARTED).subscribe(list => {
            this.refreshing = true;
        });
        this.subscription2 = this.eventService.observeTopic(SmartControlService.COMPONENTS_UPDATE_FINISHED).subscribe(list => {
            this.refreshing = false;
        });
    }

    ngOnDestroy() {
        if (this.subscription1) { this.subscription1.unsubscribe(); }
        if (this.subscription2) { this.subscription2.unsubscribe(); }
    }

    refresh() {
        this.smartControlService.updateComponents();
    }

    filter() {
    }

    getComponents(): ConnectedAssetAdministrationShell[] {
        return this.smartControlService.getComponentsAll();
    }

    getComponentsSorted(): ConnectedAssetAdministrationShell[] {
        let sorted: ConnectedAssetAdministrationShell[] = this.getComponents().sort((a, b) => {
            if (a.id.toLowerCase() > b.id.toLowerCase()) { return 1; }
            if (a.id.toLowerCase() < b.id.toLowerCase()) { return -1; }
            return 0;
        });
        return sorted;
    }

    getComponentsFilteredSorted(): ConnectedAssetAdministrationShell[] {
        let fsl = this.filterValue.toLocaleLowerCase();
        let aasFiltered: ConnectedAssetAdministrationShell[] = this.getComponents().filter((aas: ConnectedAssetAdministrationShell) =>
            aas.id.toLowerCase().indexOf(fsl) >= 0
            || aas.assetTypeDefinition.toLowerCase().indexOf(fsl) >= 0
            || aas.displayName.toLowerCase().indexOf(fsl) >= 0);

        let aasFilteredSorted: ConnectedAssetAdministrationShell[] = aasFiltered.sort((a, b) => {
            if (a.id.toLowerCase() > b.id.toLowerCase()) { return 1; }
            if (a.id.toLowerCase() < b.id.toLowerCase()) { return -1; }
            return 0;
        });
        return aasFilteredSorted;
    }

    clearFilter() {
        this.filterValue = '';
    }
}
