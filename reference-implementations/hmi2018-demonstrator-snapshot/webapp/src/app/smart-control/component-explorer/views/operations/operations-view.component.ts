import { Component, OnInit, Input } from '@angular/core';

import { DataType, SubModel, ResourcesUtil, ConnectedOperation, Parameter } from 'angular-basyx';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { EventService } from '../../../../shared/services/smart-control/event-service.service';
import { ParameterSelectionOpComponent } from '../../../common/parameter-selection/parameter-selection-op.component';

@Component({
    selector: 'operations-view',
    templateUrl: 'operations-view.component.html',
    styleUrls: ['../view.scss', 'operations-view.component.scss']
})

export class OperationsViewComponent implements OnInit {
    @Input()
    subModel: SubModel;

    hideContent: boolean = false;

    // Saved parameters: operation.name -> paramString
    operationParams: { [key: string]: object[]; } = {};

    constructor(
        private toastrService: ToastrService,
        private eventService: EventService,
        private modalService: NgbModal) { }

    ngOnInit() { }

    protected util_cache: Map<string, any> = new Map<string, any>();
    util_entries(key: string, map: Map<any, any>) { // this is an angular workaround        
        let value: any = Array.from(map.entries());

        // sort
        value = value.sort((a, b) => {
            if (a[1].name.toLowerCase() > b[1].name.toLowerCase()) { return 1; }
            if (a[1].name.toLowerCase() < b[1].name.toLowerCase()) { return -1; }
            return 0;
        });

        if (this.util_cache.has(key) === false) {
            this.util_cache.set(key, value);
        } else {
            value = this.util_cache.get(key);
        }
        return value;
    }

    convDataTypeToStr(dt: DataType): String {
        return ResourcesUtil.convertDataTypeToStr(dt);
    }

    selectParameters(operation: ConnectedOperation) {
        const modalRef = this.modalService.open(ParameterSelectionOpComponent, { size: 'lg' });
        modalRef.componentInstance.operation = operation;
        modalRef.result.then(parameters => {

            // Change to expected format, as Parameters[] come from selection now
            let params: any[] = parameters.map(elem => this.getParamInputValue(elem));

            console.log('Params: ', params);
            this.operationParams[operation.name] = params;
        }, (reason) => {
        });
    }

    getParamInputValue(param: Parameter): any {
        switch (param.datatype) {
            case 1: { return param.value ? Boolean(param.value) : false; }
            case 2:
            case 3: { return param.value ? Number(param.value) : 0; }
            default: { return param.value ? String(param.value) : ''; }
        }
    }

    getOperationParams(operation: ConnectedOperation) {
        if (this.operationParams[operation.name]) {
            console.log('GetOperationParamString: ', operation, this.operationParams[operation.name]);
            return this.operationParams[operation.name];
        }
        return '';
    }

    executeOperation(op: ConnectedOperation): void {
        console.log("Operation called [Operation call is not fully implemented yet - no return value]");
        let pars = this.operationParams[op.name];
        if (pars === undefined || pars === null) {
            pars = [];
        }
        op.call(pars).subscribe(result => {
            console.log('calls op with pars=' + pars + ' (success=' + result + ')');
        });
    }
}
