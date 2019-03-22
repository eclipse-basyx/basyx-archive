import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SmartControlComponent } from './smart-control.component';

const routes: Routes = [
    {
        path: '', component: SmartControlComponent,
        children: [
            { path: 'component-explorer', loadChildren: './component-explorer/component-explorer.module#ComponentExplorerModule' },
            { path: 'browser-om2m', loadChildren: './om2m-browser/browser.module#M2MBrowserModule' },

            { path: 'settings', loadChildren: './settings/settings.module#SettingsModule' }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SmartControlRoutingModule {

}
