import { ViewChild } from '@angular/core';
import { TreeNode, TreeComponent } from 'angular-tree-component';
import { Parameter } from './parameter';

export class TreeNodeM2M extends TreeNode {
    public static readonly LBL_COMPONENT = "basys-ty:aas"; // ty=2
    public static readonly LBL_SERVICE = "basys-ty:serv"; // ty=3
    public static readonly LBL_PROPERTY = "basys-ty:prop"; // ty=3
    public static readonly LBL_OPERATION = "basys-ty:op"; // ty=3

    @ViewChild(TreeComponent)
    private static tree: TreeComponent;
    
    name: string;
    parent: TreeNodeM2M;
    children: TreeNode[];
    m2mData: any = "[no content]";

    uril: string; // uril = path?
    icon: string;


    constructor(name: string, parent: TreeNodeM2M, m2mData: any) {
        super(m2mData, parent, TreeNodeM2M.tree.treeModel, 0)
        this.name = name;
        this.parent = parent;
        this.m2mData = m2mData;
        this.children = [];

        this.uril = (parent != null) ? parent.uril + '/' + name : name;
        this.icon = this.getIcon();

    }

    getType(): number {
        return this.m2mData.ty != undefined ? this.m2mData.ty : 0;
    }

    getLabelType(): string {
        if (this.isComponent()) { return "component" }
        if (this.isService()) { return "service" }
        if (this.isProperty()) { return "property" }
        if (this.isOperation()) { return "operation" }
        return 'none';
    }

    isComponent(): boolean {
        return this.m2mData.lbl != undefined ? this.m2mData.lbl.indexOf(TreeNodeM2M.LBL_COMPONENT) > -1 : false;
    }
    isService(): boolean {
        return this.m2mData.lbl != undefined ? this.m2mData.lbl.indexOf(TreeNodeM2M.LBL_SERVICE) > -1 : false;
    }
    isProperty(): boolean {
        return this.m2mData.lbl != undefined ? this.m2mData.lbl.indexOf(TreeNodeM2M.LBL_PROPERTY) > -1 : false;
    }
    isOperation(): boolean {
        return this.m2mData.lbl != undefined ? this.m2mData.lbl.indexOf(TreeNodeM2M.LBL_OPERATION) > -1 : false;
    }

    getIcon(): string {
        switch (this.getType()) {
            case 1:
                return 'lock'
            case 2:
                if (this.isComponent()) { return 'hdd-o'; }
            case 3:
                if (this.isProperty()) { return 'pencil'; }
                if (this.isOperation()) { return 'gears'; }
                return 'folder-o'
            case 4:
                return 'file-o'
            case 5:
                return 'sitemap'
            case 23:
                return 'comments-o'
            default:
                return 'file-image-o'
        }
    }

    isDeletable(): boolean {
        if (this.getType() === 2 || this.getType() === 3) {
            return true;
        }
        else {
            return false;
        }
    }

    getLabelParams(): Map<string, string> {
        let parameters: Map<string, string> = new Map();
        let lblStringArray: Array<string> = this.m2mData.lbl;
        lblStringArray.forEach(pair => {
            let keyValue: string[] = pair.split(':');
            parameters.set(keyValue[0], keyValue[1]);
        });
        return parameters;
    }

    getOperationParams(): Array<Parameter> {
        let labels: Map<string, string> = this.getLabelParams();

        // Extract operation params
        let params: Array<Parameter> = new Array<Parameter>();
        for (var index = 0; index < Number(labels.get('par-len')); index++) {
            params.push(new Parameter(labels.get('par-' + index + '-nm'), labels.get('par-' + index + '-dty')));
        }
        return params;
    }
}
