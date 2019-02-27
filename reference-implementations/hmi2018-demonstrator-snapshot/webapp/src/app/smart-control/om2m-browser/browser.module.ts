import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TreeModule } from 'angular-tree-component';
import { NgbModule, NgbDropdownModule } from "@ng-bootstrap/ng-bootstrap";
import { ContextMenuModule } from 'ngx-contextmenu/lib';

import { M2MBrowserComponent } from './browser.component';

import { PageHeaderModule } from "../../shared";
import { AttributeView } from "./views/attribute-view.component";
import { OperationView } from "./views/operation-view.component";
import { ValueView } from "../../smart-control/om2m-browser/views/value-view.component";
import { ReplaceSlashPipe } from './components/pipe-replace-slash';
import { CustomTreeNodeExpanderComponent } from './components/custom-tree-node-expander.component';
import { M2MBrowserRoutingModule } from './browser-routing.module';

import { HeadlineModule } from '../common/headline/headline.module';

@NgModule({
    imports: [
        CommonModule,
        PageHeaderModule,
        RouterModule,
        FormsModule,

        M2MBrowserRoutingModule,
        HeadlineModule,
        TreeModule,
        ContextMenuModule,

        NgbDropdownModule,
        NgbModule.forRoot()
    ],
    declarations: [
        M2MBrowserComponent,
        AttributeView,
        OperationView,
        ValueView,

        CustomTreeNodeExpanderComponent,
        ReplaceSlashPipe
    ],
    providers: [
    ],
    exports: [M2MBrowserComponent]
})
export class M2MBrowserModule { }
