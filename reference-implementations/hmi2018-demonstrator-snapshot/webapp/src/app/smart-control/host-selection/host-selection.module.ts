import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { HostSelectionComponent } from './host-selection.component';
import { NgbDropdownModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HostViewComponent } from '../../smart-control/host-selection/host-view.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbDropdownModule.forRoot(),
        NgbModule.forRoot()
    ],
    declarations: [
        HostSelectionComponent,
        HostViewComponent
    ],
    providers: [
    ], 
    exports: [
        HostSelectionComponent,
        HostViewComponent
    ],
    entryComponents: [
        HostViewComponent
    ]
})

export class HostSelectionModule { }