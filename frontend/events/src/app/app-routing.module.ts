import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [
  {path: '', pathMatch: 'full',  redirectTo: 'institutions'},
  {
    path: 'institutions', loadChildren: ()=> import('./institutions/institutions.module').then(m => m.InstitutionsModule)
  },

  {
    path: 'events', loadChildren: ()=> import('./events/events.module').then(m => m.EventsModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
