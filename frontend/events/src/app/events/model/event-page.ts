import { Event } from './event';

export interface EventPage {
  items: Event[];
  totalElements: number;
  totalPages?: number;
}
