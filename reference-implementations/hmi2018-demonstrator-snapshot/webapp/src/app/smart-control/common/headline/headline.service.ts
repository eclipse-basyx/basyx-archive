import { Injectable } from '@angular/core';

import { EventService } from '../../../shared/services/smart-control/event-service.service';
import { Headline } from './headline';

@Injectable()
export class HeadlineService {
    static readonly EVENT_HEADLINE_CHANGED = 'Event_Headline_Changed';

    title: string = 'serviceTitle';
    icon: string = 'pencil';

    constructor(private eventService: EventService) {
    }

    setHeadline(title: string, icon: string) {
        this.title = title;
        this.icon = icon;

        this.eventService.setTopic(HeadlineService.EVENT_HEADLINE_CHANGED, new Headline(title, icon));
    }

    getTitle(): string {
        return this.title;
    }

    getIcon(): string {
        return this.icon;
    }
}