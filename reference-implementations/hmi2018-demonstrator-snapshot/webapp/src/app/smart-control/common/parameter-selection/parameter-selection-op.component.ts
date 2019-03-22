import { Component, OnInit, Input, Renderer2, AfterViewInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConnectedOperation, Parameter, DataType, ResourcesUtil } from 'angular-basyx';


@Component({
    selector: 'parameter-selection-op',
    templateUrl: 'parameter-selection-op.component.html',
    styleUrls: ['parameter-selection-op.component.scss']
})

export class ParameterSelectionOpComponent implements OnInit, AfterViewInit {
    @Input() operation: ConnectedOperation;

    // params: Parameter[] = [];

    constructor(
        public activeModal: NgbActiveModal,
        private toastrService: ToastrService,
        private renderer: Renderer2,
        private modalService: NgbModal
    ) { }

    ngOnInit() {
    }

    ngAfterViewInit() {
        if (this.getParameters().length > 0) {
            let a = this.renderer.selectRootElement('#input-0');
            if (a) a.focus();
        }
    }

    getParameters(): Parameter[] {
        return this.operation.parameters;
    }

    getParamInputType(param: Parameter): string {
        switch (param.datatype) {
            case DataType.BOOLEAN: return 'text'; // showing 'true/false' no checkbox yet
            case DataType.FLOAT:
            case DataType.INTEGER: return 'number';
            case DataType.STRING:
            default: return 'text';
        }
    }

    isParameterBoolean(param: Parameter) {
        return param.datatype === DataType.BOOLEAN;
    }

    // For html addon
    isParameterString(param: Parameter) {
        return param.datatype === DataType.STRING;
    }
    isParameterNoStringOrBoolean(param: Parameter) {
        return !(this.isParameterBoolean(param) || this.isParameterString(param));
    }

    getDatatypeString(dt: DataType): String {
        return ResourcesUtil.convertDataTypeToStr(dt);
    }

    save() {
        // let pars: any[] = this.params.map(elem => this.getParamInputValue(elem));
        // console.log('saved pars (values only): ', pars);

        // this.activeModal.close(pars);
        // TEST
        // this.activeModal.close(this.params);
        this.activeModal.close(this.operation.parameters);
        console.log('Saved params via dialog: ', this.operation.parameters);

    }

    close() {
        this.activeModal.dismiss();
    }
}
