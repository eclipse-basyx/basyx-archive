import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ContextMenuModule } from 'ngx-contextmenu/lib';
import { NgUploaderModule } from 'ngx-uploader';
import { ToastrModule } from 'ngx-toastr';
import { SplitPaneModule } from 'ng2-split-pane/lib/ng2-split-pane';

import { OneM2MResourceManagerService, OneM2MResourceExplorerService, OneM2MHttpClientService } from 'angular-onem2m';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuard } from './shared';
import { BasicComponentsModule } from './shared/components/basic-components.module';
import { SettingsService } from './smart-control/settings/settings-service';
import { EventService } from './shared/services/smart-control/event-service.service';

// AoT requires an exported function for factories
export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function OneM2MHttpClientFactory(http: HttpClient) {
    return new OneM2MHttpClientService(http);
}

export function OneM2MResourceManagerFactory(client: OneM2MHttpClientService) {
    return new OneM2MResourceManagerService(client);
}

export function OneM2MResourceExplorerFactory(client: OneM2MHttpClientService) {
    return new OneM2MResourceExplorerService(client);
}

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),
        AppRoutingModule,

        SplitPaneModule,

        BasicComponentsModule,
        NgUploaderModule,
        CommonModule,
        ToastrModule.forRoot({
            timeOut: 3000,
            extendedTimeOut: 20,
            positionClass: 'toast-bottom-center',
            // progressBar: true,
            progressAnimation: 'decreasing',
            easeTime: 300,
            // Global options:
            maxOpened: 2, // Max toasts opened. Toasts will be queued. 0 is unlimited
            autoDismiss: false // Dismiss current toast when max is reached
        }),
        ContextMenuModule.forRoot({ useBootstrap4: true, })
    ],
    providers: [
        AuthGuard,
        SettingsService,
        EventService,
        // HostSelectionService,
        {
            provide: OneM2MHttpClientService,
            useFactory: OneM2MHttpClientFactory,
            deps: [HttpClient]
        },

        {
            provide: OneM2MResourceManagerService,
            useFactory: OneM2MResourceManagerFactory,
            deps: [OneM2MHttpClientService]
        },

        {
            provide: OneM2MResourceExplorerService,
            useFactory: OneM2MResourceExplorerFactory,
            deps: [OneM2MHttpClientService]
        }


    ],
    declarations: [AppComponent],
    bootstrap: [AppComponent]
})
export class AppModule { }
