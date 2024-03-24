import { Institution } from './../../model/Institution';
import { Component } from '@angular/core';
import { catchError, Observable, of } from 'rxjs';

import { InstitutionsService } from '../../services/institutions.service';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-institutions',
  templateUrl: './institutions.component.html',
  styleUrls: ['./institutions.component.scss']
})
export class InstitutionsComponent {


  institutions$: Observable<Institution[]> | null = null;

  constructor(
    private service: InstitutionsService,
    private router: Router,
    private route: ActivatedRoute) {
    this.institutions$ = this.service.list().pipe(
      catchError((error) => {
        this.onError('Erro ao carregar ao listar as instituições');
        return of([]);
      })
    );
  }

  add() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  view(id: string) {
    this.router.navigate(['events/view', id]);
  }

  onError(errorMsg: string) {
    // this.dialog.open(ErrorDialogComponent, {
    //   data: errorMsg,
    // });
  }

}
