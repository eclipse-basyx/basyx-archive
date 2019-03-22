import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { HostSelectionModule } from '../../../smart-control/host-selection/host-selection.module';
import { HeadlineComponent } from '../../../smart-control/common/headline/headline.component';
import { HeadlineService } from './headline.service';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        HostSelectionModule
    ],
    declarations: [
        HeadlineComponent
    ],
    providers: [
        HeadlineService
    ],
    exports: [
        HeadlineComponent
    ]
})
export class HeadlineModule { }