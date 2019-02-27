import { Injectable } from '@angular/core';

import { ConnectedAssetAdministrationShell, OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';
import { EventService } from '../shared/services/smart-control/event-service.service';

// Holds and updates currently available components
@Injectable()
export class SmartControlService {
    public static readonly COMPONENTS_CHANGED_EVENT: string = 'COMPONENTS_CHANGED_EVENT';
    public static readonly COMPONENTS_UPDATE_STARTED: string = 'COMPONENTS_UPDATE_STARTED';
    public static readonly COMPONENTS_UPDATE_FINISHED: string = 'COMPONENTS_UPDATE_FINISHED';

    aasList: ConnectedAssetAdministrationShell[] = [];

    // Update variables
    intervalUpdateComponents: number;
    updateInterval: number = 8000;
    updating: boolean = false;

    constructor(
        private scManagerService: OneM2MAssetAdministrationShellManagerService,
        private eventService: EventService
    ) {
        this.updateComponents();
        this.start();
    }

    private start() {
        this.intervalUpdateComponents = setInterval(this.updateComponents.bind(this), this.updateInterval);
    }

    /** Stop the internal update timer. (never called so far) */
    stop() {
        if (this.intervalUpdateComponents) {
            console.log('sc service stop');
            clearInterval(this.intervalUpdateComponents);
        }
    }

    isUpdating(): boolean {
        return this.updating;
    }

    async updateComponents() {
        if (!this.updating) {
            this.updating = true;
            this.eventService.setTopic(SmartControlService.COMPONENTS_UPDATE_STARTED, this.aasList);
            try {
                this.scManagerService.retrieveAASAll().subscribe(newList => {

                    // Changes happened
                    let changed: boolean = false;

                    // Newly added components
                    let aasToAdd: ConnectedAssetAdministrationShell[] = [];
                    newList.forEach(aasNew => {
                        if (!this.aasList.some(aas => aas.id === aasNew.id)) {
                            aasToAdd.push(aasNew);
                            this.aasList.push(aasNew); // test
                            changed = true;
                        }
                    });

                    // Removed components
                    let aasToDelete: ConnectedAssetAdministrationShell[] = [];
                    this.aasList.forEach((aasOld, i) => {
                        if (!newList.some(aas => aas.id === aasOld.id)) {
                            aasToDelete.push(aasOld);
                            this.aasList.splice(i, 1); // test
                            changed = true;
                        }
                    });

                    // Event
                    if (changed) {
                        // console.log('Added: ', aasToAdd);
                        // console.log('Deleted:', aasToDelete);
                        this.eventService.setTopic(SmartControlService.COMPONENTS_CHANGED_EVENT, this.aasList);
                    }

                    this.updating = false;
                    this.eventService.setTopic(SmartControlService.COMPONENTS_UPDATE_FINISHED, this.aasList);
                });
            } catch (e) {
                console.error("Failure while updating");
                console.error(e);
            }
        }
    }

    getComponentsAll(): ConnectedAssetAdministrationShell[] {
        return this.aasList;
    }

    // Sleep (async function needed, then 'await sleep(ms))
    private sleep(ms = 0) {
        return new Promise(r => setTimeout(r, ms));
    }
}
