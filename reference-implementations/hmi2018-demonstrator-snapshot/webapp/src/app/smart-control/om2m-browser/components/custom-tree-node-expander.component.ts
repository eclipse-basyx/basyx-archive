import { TreeNode } from 'angular-tree-component';
import { Component, Input, ViewEncapsulation, ElementRef } from '@angular/core';

@Component({
    selector: 'CustomTreeNodeExpander, custom-tree-node-expander',
    encapsulation: ViewEncapsulation.None,
    styleUrls: ['./custom-tree-node-expander.component.scss'],
    templateUrl: './custom-tree-node-expander.component.html'
})
export class CustomTreeNodeExpanderComponent {
    @Input() node: TreeNode;

    constructor(private elementRef: ElementRef) {
        this.deprecatedSelector('TreeNodeExpander', 'tree-node-expander', elementRef); // ?
    }

    deprecatedSelector(oldSelector, newSelector, element) {
        if (element.nativeElement.tagName && element.nativeElement.tagName.toLowerCase() === oldSelector.toLowerCase()) {
            console.warn(`If you are using the capitalized \'${oldSelector}\' selector please move to the 
      kebab-case \'${newSelector}\' selector, as the capitalized will be soon deprecated`);
        }
    }
}