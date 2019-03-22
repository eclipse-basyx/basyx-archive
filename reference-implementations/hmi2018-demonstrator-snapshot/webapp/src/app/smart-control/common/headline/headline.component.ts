import { Component, OnInit, Input, OnDestroy } from '@angular/core';

import { EventService } from '../../../shared/services/smart-control/event-service.service';
import { HeadlineService } from './headline.service';
import { Headline } from './headline';

@Component({
    selector: 'headline',
    templateUrl: 'headline.component.html'
})
export class HeadlineComponent implements OnInit, OnDestroy {
    title: string = 'starting..';
    icon: string = 'list';

    eventHeadlineChange: any;

    constructor(private headlineService: HeadlineService, private eventService: EventService) { }

    ngOnInit() {
        // Subscribe on changes
        this.eventHeadlineChange = this.eventService.observeTopic(HeadlineService.EVENT_HEADLINE_CHANGED).subscribe(
            (headline: Headline) => {
                this.setHeadline(headline);
            });
    }

    ngOnDestroy() {
        // unsubscribe from events to ensure no memory leaks
        this.eventHeadlineChange.unsubscribe();
    }

    private setHeadline(headline: Headline) {
        this.title = headline.title;
        this.icon = headline.icon;
    }

    getTitle(): string {
        return this.title;
    }

    getIcon(): string {
        return this.icon;
    }
}