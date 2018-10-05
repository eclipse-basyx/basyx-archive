import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ComponentExplorerComponent } from "./component-explorer.component";


const routes: Routes = [
    { path: '', component: ComponentExplorerComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComponentExplorerRoutingModule { }



