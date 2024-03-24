import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsComponent } from './containers/events/events.component';
import { EventsListComponent } from './components/events-list/events-list.component';


@NgModule({
  declarations: [
    EventsComponent,
    EventsListComponent
  ],
  imports: [
    CommonModule,
    EventsRoutingModule,
    AppMaterialModule
  ]
})
export class EventsModule { }
