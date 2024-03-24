import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Institution } from '../../model/Institution';

@Component({
  selector: 'app-institutions-list',
  templateUrl: './institutions-list.component.html',
  styleUrls: ['./institutions-list.component.scss']
})
export class InstitutionsListComponent {
  @Input() institutions: Institution[] = [];
  @Output() add: EventEmitter<boolean> = new EventEmitter(false);
  @Output() view: EventEmitter<string> = new EventEmitter(false);

  readonly displayedColumns = ['name', 'type', 'createdAt', 'actions']


  onAdd() {
    this.add.emit(true);

  }

  onView(id: string) {
    this.view.emit(id);
  }

}
