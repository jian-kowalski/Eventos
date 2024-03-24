import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { first } from 'rxjs';
import { EventPage } from '../model/event-page';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private readonly API = 'http://127.0.0.1:8080/events';


  constructor(private httpClient: HttpClient) {}

  list(page = 0, perPage = 10, institution: string) {
    return this.httpClient.get<EventPage>(this.API, { params: { page, perPage, institution } }).pipe(first());
  }
}
