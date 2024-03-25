import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { EventsRoutingModule } from './events-routing.module';
import { EventsComponent } from './containers/events/events.component';
import { EventsListComponent } from './components/events-list/events-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EventsFormComponent } from './containers/events-form/events-form.component';


@NgModule({
  declarations: [
    EventsComponent,
    EventsListComponent,
    EventsFormComponent,
  ],
  imports: [
    CommonModule,
    EventsRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    AppMaterialModule
  ]
})
export class EventsModule { }
