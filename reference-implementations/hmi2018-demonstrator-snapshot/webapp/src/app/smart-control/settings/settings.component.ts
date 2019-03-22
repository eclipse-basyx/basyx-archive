import { Component, OnInit } from '@angular/core';
import { HeadlineService } from '../common/headline/headline.service';
import { SettingsService } from './settings-service';

@Component({
    selector: 'settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {

    constructor(
        private settings: SettingsService,
        private headlineService: HeadlineService
    ) { }

    ngOnInit() {
        this.headlineService.setHeadline('Settings', 'gear');
    }

    get browserRefreshRate() { return this.settings.browserRefreshRate; }
    set browserRefreshRate(value) { this.settings.browserRefreshRate = value; }

    isVisible(key: string) {
        return this.settings.isVisible(key);
    }

    setVisible(event) {
        let id = event.target.id;
        let state = event.target.checked;

        this.settings.setVisible(id, state);

        // this._visibilityStates[id] = state;
        // localStorage.setItem(this.KEY_SIDEBAR_VISIBILITY, JSON.stringify(this._visibilityStates));
    }
}
