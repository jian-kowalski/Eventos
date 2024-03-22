import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EventsComponent } from './events/events/events.component';

const routes: Routes = [
  {path: '', redirectTo: 'events', pathMatch: 'full'},
  {path: 'events',
  loadChildren: () => import('./events/events.module').then(m => m.EventsModule),
  component: EventsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
