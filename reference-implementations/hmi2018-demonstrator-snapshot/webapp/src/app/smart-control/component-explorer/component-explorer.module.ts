import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { ComponentExplorerComponent } from './component-explorer.component';
import { ComponentViewComponent } from './views/component/component-view.component';
import { ComponentExplorerRoutingModule } from "./component-explorer-routing.module";
import { SubmodelViewComponent } from './views/submodel/submodel-view.component';
import { HeadlineModule } from '../common/headline/headline.module';
import { PropertiesViewComponent } from './views/properties/properties-view.component';
import { OperationsViewComponent } from './views/operations/operations-view.component';
import { EventsViewComponent } from './views/events/events-view.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        // PageHeaderModule,
        FormsModule,

        HeadlineModule,
        ComponentExplorerRoutingModule,
        NgbModule.forRoot(),
    ],
    declarations: [
        ComponentExplorerComponent,
        ComponentViewComponent,
        SubmodelViewComponent,
        PropertiesViewComponent,
        OperationsViewComponent,
        EventsViewComponent,
    ],
    exports: [
        ComponentExplorerComponent
    ]
})
export class ComponentExplorerModule { }
