import { Component, OnInit } from '@angular/core';
import { FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { InstitutionsService } from '../../services/institutions.service';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Institution } from '../../model/Institution';

@Component({
  selector: 'app-institutions-form',
  templateUrl: './institutions-form.component.html',
  styleUrls: ['./institutions-form.component.scss']
})
export class InstitutionsFormComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: InstitutionsService,
    private snackBar: MatSnackBar,
    // private dialog: MatDialog,
    private location: Location,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    const institution: Institution = {id: '', name: '', type: '', createdAt: ''};
    this.form = this.formBuilder.group({
      id: [institution.id],
      name: [
        institution.name,
        [Validators.required, Validators.minLength(5), Validators.maxLength(60)]
      ],
      type: [institution.type, [Validators.required]],
    });
  }

  onSubmit() {
    this.service.save(this.form.value as Institution).subscribe(
      () => {
        this.onSucess();
        this.onCancel();
      },
      (e) => this.onError(e.error)
    );
  }


  onCancel() {
    this.location.back();
  }

  private onSucess() {
    this.snackBar.open('Salvo com sucesso!', '', { duration: 5000 });
  }

  private onError(err: any) {}
  //   this.dialog.open(ErrorDialogComponent, {
  //     data: err,
  //   });
  // }
}
