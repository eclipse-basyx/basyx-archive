import { Injectable } from '@angular/core';

@Injectable()
export class SettingsService {
    public readonly KEY_BROWSER_REFRESH_RATE = "browserRefreshRate";
    public readonly KEY_SIDEBAR_VISIBILITY = "sidebarVisibility";

    // Background variables with default values
    public _browserRefreshEnabled: boolean = true; // not used atm, 0 is disabled
    public _browserRefreshRate: number = 8;
    private _visibilityStates: { [key: string]: boolean } = {};

    constructor() {
        this.loadBrowserRefreshRate();
        this.loadSidebarVisiblity();

        // Hide items
    }

    // Refresh rate:
    get browserRefreshRate() { return this._browserRefreshRate; }
    set browserRefreshRate(value) { this._browserRefreshRate = value; this.saveBrowserRefreshRate() }
    loadBrowserRefreshRate() { this._browserRefreshRate = Number(localStorage.getItem(this.KEY_BROWSER_REFRESH_RATE)) || this._browserRefreshRate; }
    saveBrowserRefreshRate() { localStorage.setItem(this.KEY_BROWSER_REFRESH_RATE, '' + this._browserRefreshRate); }

    // Sidebar item visibility
    setVisible(id, state) {
        // setVisible(event) {
        // let id = event.target.id;
        // let state = event.target.checked;
        this._visibilityStates[id] = state;
        localStorage.setItem(this.KEY_SIDEBAR_VISIBILITY, JSON.stringify(this._visibilityStates));
    }
    isVisible(id: any) {
        if (this._visibilityStates[id] == undefined) { this._visibilityStates[id] = true; }
        return this._visibilityStates[id];
    }
    loadSidebarVisiblity() {
        let stored = JSON.parse(localStorage.getItem(this.KEY_SIDEBAR_VISIBILITY)) as { [key: string]: boolean };
        if (stored != null) { this._visibilityStates = stored; }
    }
}
