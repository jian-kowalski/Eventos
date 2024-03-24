import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { InstitutionsFormComponent } from './containers/institutions-form/institutions-form.component';
import { InstitutionsComponent } from './containers/institutions/institutions.component';


const routes: Routes = [
  { path: '', component: InstitutionsComponent },
  {
    path: 'new',
    component: InstitutionsFormComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InstitutionsRoutingModule { }
