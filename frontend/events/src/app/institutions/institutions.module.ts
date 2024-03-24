import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AppMaterialModule } from '../shared/app-material/app-material.module';
import { InstitutionsComponent } from './containers/institutions/institutions.component';
import { InstitutionsRoutingModule } from './institutions-routing.module';
import { InstitutionsListComponent } from './components/institutions-list/institutions-list.component';
import { InstitutionsFormComponent } from './containers/institutions-form/institutions-form.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    InstitutionsComponent,
    InstitutionsListComponent,
    InstitutionsFormComponent
  ],
  imports: [
    CommonModule,
    InstitutionsRoutingModule,
    AppMaterialModule,
    ReactiveFormsModule
  ]
})
export class InstitutionsModule { }
