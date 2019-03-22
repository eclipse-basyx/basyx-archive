import { Component, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { Host } from './host';
import { HostViewComponent } from './host-view.component';
import { HostSelectionService } from './host-selection.service';

@Component({
    selector: 'host-selection',
    templateUrl: 'host-selection.component.html',
    styleUrls: ['host-selection.component.scss']
})
export class HostSelectionComponent implements OnInit {
    closeResult: string;

    constructor(
        private hostSelectionService: HostSelectionService,
        private modalService: NgbModal
    ) { }

    ngOnInit() {
    }

    add() {
        const modalRef = this.modalService.open(HostViewComponent);
        modalRef.componentInstance.title = 'Add a new host';
        modalRef.componentInstance.host = new Host('', null);
        modalRef.result.then((host) => {
            console.log('Adding new host: ', host);

            this.hostSelectionService.addHost(host);
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
            console.log('Closed/Rejected?: ', this.closeResult);
        });
    }

    edit() {
        const modalRef = this.modalService.open(HostViewComponent);
        modalRef.componentInstance.title = 'Edit the host';
        modalRef.componentInstance.host = this.hostSelectionService.getHostSelected();
        modalRef.result.then((host) => {
            console.log('Edited a host: ', host);
            this.hostSelectionService.updateHost(host);
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
            console.log('Closed/Rejected?: ', this.closeResult);
        });
    }

    remove() {
        console.log('Removing host: ', this.hostSelectionService.getHostSelected());

        this.hostSelectionService.removeHost(this.hostSelectionService.getHostSelected());
    }

    import() {
        console.log('Not yet implemented');
    }

    export() {
        console.log('Not yet implemented');
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

    getHosts(): Host[] {
        return this.hostSelectionService.getHosts();
    }

    getHostSelected(): Host {
        return this.hostSelectionService.getHostSelected();
    }

    setHostSelected(host: Host) {
        return this.hostSelectionService.setHostSelected(host);
    }

    setDefaultHosts() {
        this.hostSelectionService.setDefaultHosts();
    }
}
