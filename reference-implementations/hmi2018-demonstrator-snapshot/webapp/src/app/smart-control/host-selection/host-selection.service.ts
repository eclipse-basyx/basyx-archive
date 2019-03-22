import { Host, HostOM2M, HostIOTDM } from './host';
import { Injectable } from '@angular/core';

import { EventService } from '../../shared/services/smart-control/event-service.service';

import { OneM2MHttpClientService, OneM2MHttpClientConfiguration, OneM2MHttpClientProtocol } from 'angular-onem2m';
import { ToastrService } from 'ngx-toastr';

@Injectable()
// TODO Dont allow doubled keys (on add and edit)
export class HostSelectionService {
    public static readonly HOST_CHANGE_STARTED_EVENT: string = 'HOST_CHANGE_STARTED_EVENT';
    public static readonly HOST_CHANGE_SUCCESS_EVENT: string = 'HOST_CHANGE_SUCCESS_EVENT';
    public static readonly HOST_CHANGE_ERROR_EVENT: string = 'HOST_CHANGE_ERROR_EVENT';
    private hostList: Host[] = [];
    private hostSelected: Host;

    constructor(
        private eventService: EventService,
        private oneM2MClient: OneM2MHttpClientService,
        private toastrService: ToastrService
    ) {
        // Restore from local storage or create default host list
        let restored = this.restoreFromLocalStorage();
        console.log('Restored host settings from local storage: ', restored);

        if (!restored) {
            this.setDefaultHosts();
        }
    }

    setDefaultHosts() {
        this.hostList = [];
        this.hostList.push(new HostOM2M('Eclipse oM2M Default', 'localhost', 8080, 'admin:admin')); // default credentials for oM2M

        // Set selected host to first available
        this.setHostSelected(this.hostList[0]);
        this.saveToLocalStorage();
    }

    addHost(host: Host) {
        this.hostList.push(host);

        // Select newly added host
        this.setHostSelected(host);
        this.saveToLocalStorage();
    }

    getHosts(): Host[] {
        return this.hostList;
    }

    getHostSelected(): Host {
        return this.hostSelected;
    }

    removeHost(host: Host) {
        this.hostList = this.hostList.filter(item => item !== host);

        // Set first element in list as selected
        if (this.hostList.length > 0) {
            this.setHostSelected(this.hostList[0]);
        }

        this.saveToLocalStorage();
    }

    setHostSelected(host: Host) {

        let config = new OneM2MHttpClientConfiguration(OneM2MHttpClientProtocol.HTTP, host.serverUrl, host.port, host.from);
        this.eventService.setTopic(HostSelectionService.HOST_CHANGE_STARTED_EVENT, host);
/*
        this.oneM2MClient.start(config).subscribe(
            result => {
                console.log('Host selected: ', host);
                this.toastrService.success('Host change successful');
                this.hostSelected = host;
                this.saveToLocalStorage();
                this.eventService.setTopic(HostSelectionService.HOST_CHANGE_SUCCESS_EVENT, host);
            }, error => {
                console.log('Host NOT changed: ', host);
                this.toastrService.warning('Changing host to "' + host.key + '" failed.');
                this.eventService.setTopic(HostSelectionService.HOST_CHANGE_ERROR_EVENT, host);
            }
        );*/
    }

    // Existing host configuration gets updated
    updateHost(host: Host) {
        let i = this.hostList.indexOf(host);
        if (i > -1) {
            this.hostList.splice(i, 1, host);
        }

        this.setHostSelected(host);
        this.saveToLocalStorage();
    }

    restoreFromLocalStorage(): boolean {
        let hostListJson = localStorage.getItem('hostList');
        let hostSelectedIndexJson = localStorage.getItem('hostSelectedIndex');

        if (hostListJson && hostSelectedIndexJson) {
            this.hostList = JSON.parse(hostListJson) as Host[];

            if (hostSelectedIndexJson) {
                let i: number = JSON.parse(hostSelectedIndexJson);
                if (this.hostList[i]) {                    
                    this.setHostSelected(this.hostList[i]);
                    return true;
                }
            }
        }
        return false;
    }

    saveToLocalStorage() {
        localStorage.setItem('hostList', JSON.stringify(this.hostList));
        localStorage.setItem('hostSelectedIndex', JSON.stringify(this.hostList.indexOf(this.hostSelected)));
    }
}
