import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsComponent } from './events/events.component';


@NgModule({
  declarations: [
    EventsComponent
  ],
  imports: [
    CommonModule,
    EventsRoutingModule,
    AppMaterialModule
  ]
})
export class EventsModule { }
