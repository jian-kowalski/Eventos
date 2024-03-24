import { EventsService } from '../../services/events.service';
import { Component, OnInit } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { EventPage } from '../../model/event-page';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

  events$: Observable<EventPage> | null = null;

  id: string | undefined;

  pageIndex = 0;
  pageSize = 10;

  constructor(readonly eventsService: EventsService,
    private route: ActivatedRoute) {
    // this.events$ = this.eventsService.list();
  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.refresh();
  }

  add() {
    throw new Error('Method not implemented.');
  }

  refresh(pageEvent: PageEvent = { length: 0, pageIndex: 0, pageSize: 10 }) {
    this.events$ = this.eventsService
      .list(pageEvent.pageIndex, pageEvent.pageSize, this.id ? this.id: '')
      .pipe(
        tap(() => {
          this.pageIndex = pageEvent.pageIndex;
          this.pageSize = pageEvent.pageSize;
        }),
        catchError(() => {
          this.onError('Error.');
          return of({ items: [], totalElements: 0 } as EventPage);
        })
      );
  }

  onError(errorMsg: string) {
    // this.dialog.open(ErrorDialogComponent, {
    //   data: errorMsg
    // });
  }

}
