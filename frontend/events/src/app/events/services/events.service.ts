import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { first } from 'rxjs';
import { EventPage } from '../model/event-page';
import { Event } from '../model/event';
import { EventCreate } from '../model/event-request';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private readonly API = 'http://127.0.0.1:8080/events';


  constructor(private httpClient: HttpClient) {}

  list(page = 0, perPage = 10, institution: string) {
    return this.httpClient.get<EventPage>(this.API, { params: { page, perPage, institution } }).pipe(first());
  }

  save(record: EventCreate) {
    console.log(record);

    return this.httpClient.post<Event>(this.API, record).pipe(first());
   }
}
