import { Component, Input } from '@angular/core';

import { Event } from '../../model/event';
@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent {

  @Input() events: Event[] = [];
  displayedColumns: string[] = ['name', 'active', 'startAt', 'finishAt', 'createdAt'];

  constructor() {
  }


}
