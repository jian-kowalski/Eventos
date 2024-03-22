import { Injectable } from '@angular/core';
import { Event } from '../events/model/event';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(private httpClient: HttpClient) { }

  list(): Event[] {
    return [
      {
        id: '1',
        name: 'Event 1',
        active: true,
        startAt: '2022-01-01',
        finishAt: '2022-01-31',
        createdAt: '2022-01-01'
      },
      {
        id: '2',
        name: 'Event 2',
        active: true,
        startAt: '2022-01-01',
        finishAt: '2022-01-31',
        createdAt: '2022-01-01'
      },
    ];
  }
}
