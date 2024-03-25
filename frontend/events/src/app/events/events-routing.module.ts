import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EventsComponent } from './containers/events/events.component';
import { EventsFormComponent } from './containers/events-form/events-form.component';

const routes: Routes = [
  {path: '', component: EventsComponent},
  {path: 'new', component: EventsFormComponent},
  {path: 'view/:id', component: EventsComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventsRoutingModule { }
