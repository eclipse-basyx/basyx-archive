import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-smart-control',
    templateUrl: './smart-control.component.html',
    styleUrls: ['./smart-control.component.scss']
})
export class SmartControlComponent implements OnInit {
       
    constructor(public router: Router) { }

    ngOnInit() {
        if (this.router.url === '/') {
            this.router.navigate(['/browser-om2m']);
        }
    }
}
