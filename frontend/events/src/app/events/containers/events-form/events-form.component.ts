import { Router } from '@angular/router';
import { DatePipe, Location } from '@angular/common';
import { Component } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { Institution } from 'src/app/institutions/model/Institution';
import { InstitutionsService } from 'src/app/institutions/services/institutions.service';

import { EventsService } from '../../services/events.service';
import { EventCreate } from '../../model/event-request';

@Component({
  selector: 'app-events-form',
  templateUrl: './events-form.component.html',
  styleUrls: ['./events-form.component.scss']
})
export class EventsFormComponent {
  institutions$: Observable<Institution[]> | null = null;
  form!: FormGroup;
  id: string = '';

  constructor(
    private formBuilder: NonNullableFormBuilder,
    readonly service: EventsService,
    readonly instituitionsService: InstitutionsService,
    private snackBar: MatSnackBar,
    // private dialog: MatDialog,
    private location: Location,
    private router: Router
  ) {
    this.institutions$ = this.instituitionsService.list();
  }

  ngOnInit(): void {
    const event: EventCreate = { name: '', startDate: new Date().toISOString(), endDate: new Date().toISOString(), institution: '' };
    this.form = this.formBuilder.group({
      name: [
        event.name,
        [Validators.required, Validators.minLength(3), Validators.maxLength(60)]
      ],
      startDate: [event.startDate, [Validators.required]],
      endDate: [event.endDate, [Validators.required]],
      institution: [event.institution, [Validators.required]],
    });
  }

  onSubmit() {
    let event = this.form.value as EventCreate;
    const startAt = new DatePipe('pt-BR').transform(event.startDate, 'yyyy-MM-dd HH:mm:ss');
    const endDate = new DatePipe('pt-BR').transform(event.endDate, 'yyyy-MM-dd HH:mm:ss');
    const eventRequest: EventCreate = {
      name: event.name,
      startDate: startAt == null ? '' : startAt,
      endDate: endDate == null ? '' : endDate,
      institution: event.institution
    }

    this.service.save(eventRequest).subscribe(
      () => {
        this.onSucess();
        this.onCancel();
      },
      (e) => this.onError(e.error)
    );
    // this.router.navigate(['events/view/', event.institution])
  }


  onCancel() {
    this.location.back();
  }

  private onSucess() {
    this.snackBar.open('Salvo com sucesso!', '', { duration: 5000 });
  }

  private onError(err: any) { }
  //   this.dialog.open(ErrorDialogComponent, {
  //     data: err,
  //   });
  // }
}
