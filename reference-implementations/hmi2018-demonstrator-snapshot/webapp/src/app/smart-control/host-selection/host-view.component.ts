import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Host } from './host';

@Component({
  selector: 'host-view-component',
  templateUrl: 'host-view.component.html',
  styleUrls: ['host-view.component.scss']
})
export class HostViewComponent {
  @Input() title;
  @Input() host: Host;

  constructor(public activeModal: NgbActiveModal) { }
}