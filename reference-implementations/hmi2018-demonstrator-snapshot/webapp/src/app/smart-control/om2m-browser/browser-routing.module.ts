import { NgModule, OnInit, AfterViewInit, OnDestroy } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { M2MBrowserComponent } from './browser.component';

const routes: Routes = [
  { path: '', component: M2MBrowserComponent, data: { title: 'My bb', icon: '43' } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class M2MBrowserRoutingModule implements OnInit, AfterViewInit, OnDestroy {

  constructor() {
    // console.log('CONSTR: M2MBrowserRoutingModule');
  }

  // NEVER CALLED:

  ngOnInit() {
    console.log('ROUTING ON INIT');
  }

  ngAfterViewInit() {
    console.log('ROUTING AFTER VIEW INIT');
  }

  ngOnDestroy() {
    console.log('ROUTING ON DESTROY');
  }
}
