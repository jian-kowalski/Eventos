import { InstitutionsService } from './../../../institutions/services/institutions.service';
import { EventsService } from '../../services/events.service';
import { Component, OnInit } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { EventPage } from '../../model/event-page';
import { Institution } from 'src/app/institutions/model/Institution';
import { FormControl, FormGroup } from '@angular/forms';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';

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

  institutions$: Observable<Institution[]> | null = null;

  id: string | undefined;
  pageIndex = 0;
  pageSize = 10;

  constructor(readonly eventsService: EventsService,
    readonly instituitionsService: InstitutionsService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute) {
    this.institutions$ = this.instituitionsService.list();
  }
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.refresh();
  }

  add() {
    console.log(this.route);
    this.router.navigate(['events/new']);
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

  onFilter(){
    this.refresh();
  }

  onError(errorMsg: any){
    this.dialog.open(ErrorDialogComponent, {
      data: errorMsg
    });
  }

}
