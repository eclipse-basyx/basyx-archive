import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        TranslateModule,
        NgbDropdownModule.forRoot()
    ],
    declarations: [
        HeaderComponent,
        SidebarComponent
    ],
    providers: [
    ],
    exports: [
        HeaderComponent,
        SidebarComponent
    ]
})

export class BasicComponentsModule { }