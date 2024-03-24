import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'events';


  constructor(private router: Router) { }

  onNavigate(uri: string) {
    this.router.navigate([uri]);
  }



}
