import { Component, ViewChild, OnInit, AfterViewInit, OnDestroy, ElementRef } from '@angular/core';
import { ReplaySubject } from 'rxjs/ReplaySubject';
import { ContextMenuComponent } from "ngx-contextmenu/lib";
import { TreeComponent, TreeNode, IActionMapping, TREE_ACTIONS, KEYS } from 'angular-tree-component';
import { ToastrService } from "ngx-toastr";

import { routerTransition } from '../../router.animations';
import { HeadlineService } from '../common/headline/headline.service';
import { HostSelectionService } from '../host-selection/host-selection.service';

import { OneM2MResourceExplorerService, OneM2MResourcesUtil, OneM2MHttpClientService } from 'angular-onem2m';
import { OneM2MHttpClientConfiguration, OneM2MResourceManagerService } from 'angular-onem2m';

import { NodeM2M } from './resources/nodeM2M';
import { EventService } from '../../shared/services/smart-control/event-service.service';
import { SettingsService } from '../settings/settings-service';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'm2m-browser',
    templateUrl: "browser.component.html",
    styleUrls: ['browser.component.scss', '../smart-control.component.scss'],
    animations: [routerTransition()]
    // encapsulation: ViewEncapsulation.None  // Enable dynamic HTML styles 
})
export class M2MBrowserComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild(TreeComponent)
    private tree: TreeComponent;

    @ViewChild('itemContextMenu')
    public itemMenu: ContextMenuComponent;

    // Nodes in the tree
    nodes: NodeM2M[] = [];
    // Selected element
    selectedNode: NodeM2M;
    // Dictionary like [ri => NodeM2M], needs pipe to iterate
    nodeMap: { [key: string]: NodeM2M; } = {};

    updating: boolean = false;
    reloading: boolean = false;

    hostChanging: boolean = false;

    actionMapping: IActionMapping = {
        mouse: {
            dblClick: TREE_ACTIONS.TOGGLE_EXPANDED
        }
    }

    options: any = {
        getChildren: () => new Promise((resolve, reject) => {
            console.log("getChildren.. (logged from tree options)");
        }),
        animateExpand: true,
        animateSpeed: 50,
        animateAcceleration: 2,
        levelPadding: 28,
        actionMapping: this.actionMapping
    }

    eventHostChangeStarted: any;
    eventHostChangeSuccess: any;
    eventHostChangeError: any;// Subscription;
    intervalUpdateTree: number;

    /* Constructor. */
    constructor(
        private resourceExplorer: OneM2MResourceExplorerService,
        private resourceManager: OneM2MResourceManagerService,
        private eventService: EventService,
        private toastrService: ToastrService,
        private settings: SettingsService,
        private hostSelectionService: HostSelectionService,
        private oneM2MClient: OneM2MHttpClientService,
        private headlineService: HeadlineService
    ) {

        // this.currentHeight=document.documentElement.clientHeight;    
    };

    ngOnInit() {
        this.headlineService.setHeadline('OneM2M Browser', 'align-left');

        this.reload(); // if cse not reachable, => timeout, app crash, as no feedback

        // init updateInterval
        if (this.settings.browserRefreshRate > 0) {
            this.intervalUpdateTree = setInterval(this.update.bind(this), this.settings.browserRefreshRate * 1000);
        }

        // subscribe to event messages
        this.eventHostChangeStarted = this.eventService.observeTopic(HostSelectionService.HOST_CHANGE_STARTED_EVENT).subscribe(res => {
            this.hostChanging = true;
            this.clearTree();
        });
        this.eventHostChangeSuccess = this.eventService.observeTopic(HostSelectionService.HOST_CHANGE_SUCCESS_EVENT).subscribe(res => {
            this.hostChanging = false;
            this.reload(); // Reload needed to update tree after selection change..
        });
        this.eventHostChangeError = this.eventService.observeTopic(HostSelectionService.HOST_CHANGE_ERROR_EVENT).subscribe(res => {
            this.hostChanging = false;
        });
    }

    ngAfterViewInit() { }

    ngOnDestroy() {
        // stop update interval
        clearInterval(this.intervalUpdateTree);

        // unsubscribe from events to ensure no memory leaks
        this.eventHostChangeStarted.unsubscribe();
        this.eventHostChangeSuccess.unsubscribe();
        this.eventHostChangeError.unsubscribe();
    }

    // Reload the complete tree
    reload() {
        if (this.reloading || this.hostChanging) {
            console.log('Already reloading');
            return;
        }
        this.reloading = true;
        let config: OneM2MHttpClientConfiguration = this.oneM2MClient.getConfiguration();
        console.log('Reloading tree (' + config.host + ':' + config.port + ')');

        // Create tree 
        this.createTree().subscribe(
            success => {
                // console.log('s: ', success);
                this.updateSelectedNode();
                this.reloading = false;
                // console.log('TREE: ', this.tree);
                // this.expandRootNode(); //tree undefined..
            },
            error => {
                // console.log('e r: ', error);
                this.reloading = false;
            }
        );
    }

    // Update the nodes in the tree
    async update() {
        if (this.updating || this.reloading || this.hostChanging) {
            console.log('Already updating or reloading');
            return;
        }
        console.log('Updating tree (' + this.settings.browserRefreshRate + 's interval)');
        this.updating = true;

        await this.sleep(1); // Testing

        // Update tree
        this.updateTree().subscribe(
            success => {
                this.updateSelectedNode();
                this.updating = false;
            },
            error => {
                // console.log('e u: ', error);
                this.updating = false;
            }
        );
    }

    private expandRootNode() {
        // console.log(this.tree);
        this.tree.treeModel.getFirstRoot().expand();
    }


    private createTree(): Observable<boolean> {
        let subject: ReplaySubject<boolean> = new ReplaySubject<boolean>(1);
        this.nodeMap = {};
        this.nodes = [];
        this.resourceExplorer.retrieveResourceUrils('', false).subscribe(
            result => {
                let urils: string[] = result.data;

                // Finish, if no data found
                if (!urils) {
                    console.log('No data found during creation of the tree.');
                    subject.next(false);
                    subject.complete();
                }

                urils.forEach(uril => {
                    let parentNodeM2M = this.nodeMap[this.getUrilPartParent(uril)];

                    // Handle root node
                    if (!parentNodeM2M) {
                        let nodeM2M = new NodeM2M(uril, undefined, '');
                        nodeM2M.isExpanded = true;
                        this.nodeMap[uril] = nodeM2M;
                        this.nodes = [nodeM2M];
                        this.selectedNode = nodeM2M;
                    }

                    // Handle childs
                    else {
                        let nodeM2M = new NodeM2M(this.getUrilPartName(uril), parentNodeM2M, '');
                        this.nodeMap[uril] = nodeM2M;
                        parentNodeM2M.children.push(nodeM2M);

                        // Sort childs (after each insertion so far)
                        parentNodeM2M.children.sort((c1, c2) => {
                            if (c1.name.toLowerCase() > c2.name.toLowerCase()) { return 1; }
                            if (c1.name.toLowerCase() < c2.name.toLowerCase()) { return -1; }
                            return 0;
                        });
                    }
                });

                subject.next(true);
                subject.complete();
            }, error => {
                subject.error(false);
            });
        return subject.asObservable();
    }

    private updateTree(): Observable<boolean> {
        let subject: ReplaySubject<boolean> = new ReplaySubject<boolean>(1);
        this.resourceExplorer.retrieveResourceUrils('', false).subscribe(
            result => {
                let urils: string[] = result.data;

                // Finish, if no data found
                if (!urils) {
                    console.log('No data found during update of the tree.');
                    subject.next(false);
                    subject.complete();
                }

                // Add newly added nodes
                let urilsToAdd: string[] = urils.filter(item => !this.nodeMap[item]);
                urilsToAdd.forEach(uril => {
                    let parentNodeM2M = this.nodeMap[this.getUrilPartParent(uril)];
                    let nodeM2M = new NodeM2M(this.getUrilPartName(uril), parentNodeM2M, '');
                    this.nodeMap[uril] = nodeM2M;

                    if (parentNodeM2M) {
                        parentNodeM2M.children.push(nodeM2M);

                        // Sort childs
                        parentNodeM2M.children.sort((c1, c2) => {
                            if (c1.name.toLowerCase() > c2.name.toLowerCase()) { return 1; }
                            if (c1.name.toLowerCase() < c2.name.toLowerCase()) { return -1; }
                            return 0;
                        });
                    }
                });

                // Delete removed nodes
                let urilsToDelete: string[] = [];
                for (let [key, value] of Object.entries(this.nodeMap)) {
                    if (urils.indexOf(key) < 0) {
                        urilsToDelete.push(key);
                    }
                }
                urilsToDelete.forEach(uril => {
                    // Delete from map
                    delete this.nodeMap[uril];
                    // Delete from parent (if still existent)
                    let parentNodeM2M = this.nodeMap[this.getUrilPartParent(uril)];
                    if (parentNodeM2M) {
                        parentNodeM2M.children = parentNodeM2M.children.filter(item => item.uril != uril);
                    }
                });
                this.tree.treeModel.update();
                subject.next(true);
                subject.complete();
            },
            error => {
                subject.error(false);
            });
        return subject.asObservable();
    }

    private updateSelectedNode() {
        if (this.selectedNode && this.nodeMap[this.selectedNode.uril]) {
            this.updateM2MData(this.selectedNode.uril);
            return;
        } else {
            // Nomore existent, try switch to root atm
            if (this.nodeMap[this.selectedNode.parent.uril]) {
                this.selectedNode = this.selectedNode.parent;
                this.tree.treeModel.setFocusedNode(this.tree.treeModel.getFirstRoot());
                return;
            }
        }
        console.log('Not updated: ', this.selectedNode, this.nodeMap);
    }

    // Updates the onem2m data fields of the given uril and xxx? childs
    private updateM2MData(uril: string) {
        if (!uril) { return; }

        this.resourceExplorer.retrieveResourceWithChildrenRecursive(uril, false, 0).subscribe(result => {
            if (!this.nodeMap[uril]) { return; }
            let m2mData = result.resource;
            this.nodeMap[uril].m2mData = m2mData;

            // Take rn as name instead of uril
            this.nodeMap[uril].name = m2mData.rn;

            // Fill next childs
            let childsData = OneM2MResourcesUtil.getChildrenArray(m2mData);
            // console.log('ChildsData: ', childsData);
            if (childsData) {
                childsData.forEach(childData => {
                    let childUril = uril + '/' + childData.rn;

                    if (this.nodeMap[childUril] != null) {
                        this.nodeMap[childUril].m2mData = childData;
                    }
                });
            }
        });
    }

    private clearTree() {
        this.nodeMap = {};
        this.nodes = [];
        this.selectedNode = null;
    }

    private getUrilPartName(uril: any): string {
        let splitPos = uril.lastIndexOf('/');
        return uril.substring(splitPos + 1);
    }

    private getUrilPartParent(uril: String): string {
        let splitPos = uril.lastIndexOf('/');
        return uril.substring(0, splitPos);
    }

    // private getSelectedCSE() {
    //     return this.connector.getSelectedCSE();
    // }

    private getInnerElement(resource: any): any {
        return resource[Object.keys(resource)[0]];
    }

    // Events

    event(event: any) {
        // console.log('EventName: ', event.eventName);
    }

    activate(event: any) {
        if (event.eventName === 'activate') {
            let nodeM2M = event.node.data as NodeM2M;
            this.selectedNode = nodeM2M;
            console.log('Activated: ', nodeM2M);
        }
    }

    export(event: any) {
        console.log('Not yet implemented with oM2M');

        let nodeM2M = event.item.data as NodeM2M;
    }

    /* IMPORT JSON LOGIC */
    @ViewChild('importJsonInput') importJsonInput: ElementRef;
    selectedImportNodePath: string;

    import(event: any) {
        // Get path from clicked element
        let nodeM2M = event.item.data as NodeM2M;
        this.selectedImportNodePath = nodeM2M.path;

        // Trigger selection dialog
        let mevent = new MouseEvent('click', { bubbles: false });
        this.importJsonInput.nativeElement.dispatchEvent(mevent);
    }

    importFileSelected(event: any) {
        let reader = new FileReader();
        reader.onload = (callback) => {
            // Create json structure 
            let array = JSON.parse(reader.result);
            let map = {};

            // Create parent
            let m2mDataP = this.getInnerElement(array[0]);
            let objP = { ri: m2mDataP.ri, pi: m2mDataP.pi.split('/')[2], rn: m2mDataP.rn, m2mData: m2mDataP, children: [] };
            map[objP.ri] = objP;

            // Create children
            for (let i = 1; i < array.length; i++) {
                let m2mData = this.getInnerElement(array[i]);
                let obj = { ri: m2mData.ri, pi: m2mData.pi.split('/')[2], rn: m2mData.rn, m2mData: m2mData, children: [] };
                map[obj.ri] = obj;
                map[obj.pi].children.push(obj);
            }

            // Create elements on saved path recursively
            let parentUri = this.selectedImportNodePath;
            this.createElementsRecursively(parentUri, objP);
        }

        // Trigger read of input file
        let input = event.target;
        reader.readAsText(input.files[0]);
    }

    createElementsRecursively(uri, data) {
        console.log('Not yet implemented with oM2M');

    }

    toggleExpanded(event: any) {
        let nodeM2M: NodeM2M = event.node.data;
        this.updateM2MData(nodeM2M.uril);
    }

    initialized(event: any) {
    }

    deleteItem(msg: string, treeNode: TreeNode) {
        let nodeM2M: NodeM2M = treeNode.data;
        this.resourceManager.deleteResource(nodeM2M.path, false).subscribe();
        treeNode.hide();
    }

    isDeletable(treeNode: TreeNode): boolean {
        let nodeM2M: NodeM2M = treeNode.data as NodeM2M;
        return nodeM2M.isDeletable();
    }

    contextMenuClosed(msg: string, item: any) {
    }

    // SEARCH
    filter(text: string) {
        // console.log('Filtering: ', text);        
        this.tree.treeModel.filterNodes(text);
    }

    clearFilter() {
        this.filter('');
    }

    // Sleep (async function needed, then 'await sleep(ms))
    private sleep(ms = 0) {
        return new Promise(r => setTimeout(r, ms));
    }
}