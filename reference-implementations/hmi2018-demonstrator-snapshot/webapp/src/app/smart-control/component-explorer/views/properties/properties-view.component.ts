
import { Component, OnInit, Input } from '@angular/core';
import { SubModel, ConnectedProperty, ConnectedCollectionProperty, ConnectedSingleProperty, DataType, ConnectedSubModel } from 'angular-basyx';

@Component({
    selector: 'properties-view',
    templateUrl: 'properties-view.component.html',
    styleUrls: ['../view.scss', 'properties-view.component.scss']
})

export class PropertiesViewComponent implements OnInit {
    @Input()
    subModel: SubModel;

    hideContent: boolean = false;

    private parameterInputValues: any[] = [];

    constructor() { }

    ngOnInit() { 
        (this.subModel[1] as ConnectedSubModel).properties.forEach( prop => {
            this.updateProperty(prop);
        });
    }

    protected util_cache: Map<string, any> = new Map<string, any>();
    util_entries(key: string, map: Map<any, any>) { // this is an angular workaround        
        var value: any = Array.from(map.entries());
        if (this.util_cache.has(key) === false) {
            this.util_cache.set(key, value);
        } else {
            value = this.util_cache.get(key);
        }
        return value;
    }

    updateProperty(prop: ConnectedProperty): void {
        if (prop instanceof ConnectedSingleProperty) {
            (prop as ConnectedSingleProperty).get().subscribe(result => {
                this.parameterInputValues[(prop as any)._m2m_ri_DATA] = result;
            });
        } else if (prop instanceof ConnectedCollectionProperty) {

        }
    }

    setProperty(prop: ConnectedProperty): void {
        if (prop instanceof ConnectedSingleProperty) {
            (prop as ConnectedSingleProperty).set(this.parameterInputValues[(prop as any)._m2m_ri_DATA]).subscribe(result => {
            });
        } else if (prop instanceof ConnectedCollectionProperty) {
        }
    }

    getDataTypeString(input: any): string {
        return DataType[input];
    }
}