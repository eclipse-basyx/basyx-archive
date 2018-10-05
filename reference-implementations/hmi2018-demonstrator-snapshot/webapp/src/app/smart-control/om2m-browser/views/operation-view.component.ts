import { Component, Input, OnInit } from '@angular/core';
import { NodeM2M } from '../resources/nodeM2M';
import { Parameter } from '../resources/parameter';
import { OneM2MAssetAdministrationShellManagerService } from 'angular-basyx';

@Component({
    selector: 'operation-view',
    templateUrl: 'operation-view.component.html',
    styleUrls: ['operation-view.component.scss']
})
export class OperationView implements OnInit {
    @Input()
    selectedNode: NodeM2M;
    returnMessage: any;
    hideContent: boolean = false;

    constructor(
        private smartcontrolManagerService: OneM2MAssetAdministrationShellManagerService
    ) {
    }

    ngOnInit(): void {
    }

    labels(): Array<any> {
        return this.selectedNode.m2mData.lbl;
    }

    getReturnType(): string {
        return this.selectedNode.getLabelParams().get('rdty');
    }

    hasParams(): boolean {
        return this.selectedNode.getOperationParams().length > 0;
    }

    stringify(variable: any) {
        return JSON.stringify(variable);
    }

    executeOperation() {
        let operation = this.selectedNode;
        let submodel = operation.parent;
        let aas = submodel.parent;
        this.smartcontrolManagerService.retrieveAAS(aas.name).subscribe(aas => {
            if (aas) {
                aas.subModels.get(submodel.name).operations.get(operation.name).call([]);
                console.log('called op');
            }
        });
    }

    executeOperationWithParameters(parameterString: string) {
        console.log('Executing op with parameters: ', parameterString);
        console.log('Not implemented yet with om2m');
    }

    getSignature(): string {
        let signature = "";
        let params: Array<Parameter> = this.selectedNode.getOperationParams();
        params.forEach(param => {
            signature = signature.concat(param.name, ' : ', param.value, ', ');
        });
        // Remove last signs
        signature = signature.substring(0, signature.length - 2);
        return signature;
    }

    // Used for input fields: https://github.com/angular/angular/issues/10423
    trackByIndex(index: number, value: string) {
        return index;
    }

}
