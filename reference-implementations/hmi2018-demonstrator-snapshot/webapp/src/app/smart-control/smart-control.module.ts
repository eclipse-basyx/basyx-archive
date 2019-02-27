import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { NgbDropdownModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';

import { SmartControlRoutingModule } from './smart-control-routing.module';
import { SmartControlComponent } from './smart-control.component';
import { BasicComponentsModule } from '../shared/components/basic-components.module';

import { OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';
import { OneM2MResourceManagerService } from 'angular-onem2m';

import { SmartControlService } from './smart-control.service';
import { HostSelectionService } from './host-selection/host-selection.service';
import { HeadlineModule } from './common/headline/headline.module';
import { ParameterSelectionOpComponent } from './common/parameter-selection/parameter-selection-op.component';


export function SmartControlAssetAdministrationShellManagerFactory(onem2mResourceManager: OneM2MResourceManagerService) {
    return new OneM2MAssetAdministrationShellManagerService(onem2mResourceManager);
}

@NgModule({
    imports: [
        CommonModule,
        NgbDropdownModule.forRoot(),
        SmartControlRoutingModule,
        TranslateModule,
        FormsModule,
        BasicComponentsModule,
        HeadlineModule
    ],
    declarations: [
        SmartControlComponent,
        ParameterSelectionOpComponent,
    ],
    providers: [
        {
            provide: OneM2MAssetAdministrationShellManagerService,
            useFactory: SmartControlAssetAdministrationShellManagerFactory,
            deps: [OneM2MResourceManagerService]
        },
        SmartControlService,
        HostSelectionService
    ],
    entryComponents: [
        ParameterSelectionOpComponent
    ]
})
export class SmartControlModule { }
